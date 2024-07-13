package com.spotify.service.impl;

import com.spotify.controller.request.CountModelRequest;
import com.spotify.dto.converter.AlbumConverter;
import com.spotify.dto.converter.ArtistConverter;
import com.spotify.dto.converter.GenresConverter;
import com.spotify.dto.converter.SongConverter;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.AlbumSongResponseType;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.dto.response.SongResponseType;
import com.spotify.entities.Songs;
import com.spotify.exception.NotFoundEntityException;
import com.spotify.repository.*;
import com.spotify.service.SongService;
import com.spotify.ultils.Constraints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final SongConverter songConverter;
    private final ArtistSongRepository artistSongRepository;
    private final GenresRepository genresRepository;
    private final GenresConverter genresConverter;
    private final AlbumRepository albumRepository;
    private final AlbumSongRepository albumSongRepository;
    private final AlbumConverter albumConverter;
    private final ArtistConverter artistConverter;
    private final ArtistRepository artistRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongConverter songConverter, ArtistSongRepository artistSongRepository, GenresRepository genresRepository, GenresConverter genresConverter, AlbumRepository albumRepository, AlbumSongRepository albumSongRepository, AlbumConverter albumConverter, ArtistConverter artistConverter, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.songConverter = songConverter;
        this.artistSongRepository = artistSongRepository;
        this.genresRepository = genresRepository;
        this.genresConverter = genresConverter;
        this.albumRepository = albumRepository;
        this.albumSongRepository = albumSongRepository;
        this.albumConverter = albumConverter;
        this.artistConverter = artistConverter;
        this.artistRepository = artistRepository;
    }

    @Override
    public SongResponseType save(SongResponseType songResponseType) {
        return null;
    }

    @Override
    public SongResponseType update(SongResponseType songResponseType) {
        return null;
    }

    @Cacheable(cacheNames = CACHE.SONG_KEY, key = "#id")
    @Override
    public SongResponseType findById(Integer id) {
        Optional<Songs> song = songRepository.findById(id);
        if (song.isPresent()) {
            var songResponseType = songConverter.convertToDTO(song.get());
            this.enrichInfos(List.of(songResponseType));
            return songResponseType;
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.SONG_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_TOP_LIST_KEY, allEntries = true),
    })
    @Override
    public boolean delete(Integer id) {
        Optional<Songs> songs = songRepository.findById(id);
        if (songs.isPresent()) {
            songRepository.deleteById(id);
            return true;
        } else if (!songs.isPresent()) {
            throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
        }
        return false;
    }

    @Cacheable(cacheNames = CACHE.SONG_LIST_KEY, key = "{#request.page,#request.size,#request.field,#request.order,#request.search}")
    @Override
    public Map<String, Object> paginationSongs(PaginationRequest request) {
        Pageable pageable = null;
        Map<String, Object> result = new HashMap<>();
        if (request.getPage() > 0) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        }
        if (request.getOrder().equals("asc")) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getField()).ascending());
        }
        if (request.getOrder().equals("desc")) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getField()).descending());
        }
        Page<Songs> songsPage = songRepository.paginationSongs(pageable, !StringUtils.isEmpty(request.getSearch()) ? request.getSearch().trim().toLowerCase() : null);
        List<SongResponseType> songResponseTypes = songsPage.toList().stream().map(songs -> songConverter.convertToDTO(songs)).collect(Collectors.toList());
        this.enrichInfos(songResponseTypes);

        result.put("songs", songResponseTypes);
        result.put("totalPages", songsPage.getTotalPages());
        result.put("totalElements", songsPage.getTotalElements());
        result.put("currentPage", request.getPage());
        return result;
    }

    private void enrichInfos(List<SongResponseType> songResponseTypes) {
        var genres = genresRepository.findAllBySongIds(songResponseTypes.stream().map(f -> f.getId()).collect(Collectors.toList())).stream().map(f -> genresConverter.convertToDTO(f)).collect(Collectors.toList());
        var songIds = songResponseTypes.stream().map(f -> f.getId()).collect(Collectors.toList());
        var albumSongs = albumSongRepository.findBySongIds(songIds);
        var albumSongGroup = albumSongs.stream().collect(Collectors.groupingBy(f -> f.getAlbumSongId().getSongs().getId()));
        var albumIds = albumSongs.stream().map(f -> f.getAlbumSongId().getAlbums().getId()).collect(Collectors.toList());
        var albumMap = albumRepository.findAllById(albumIds).stream().collect(Collectors.toMap(f -> f.getId(), Function.identity()));
        var artistSongs = artistSongRepository.findBySongIds(songIds);
        var artistIds = artistSongs.stream().map(f -> f.getArtistSongId().getArtists().getId()).collect(Collectors.toList());
        var artistMap = artistRepository.findAllById(artistIds).stream().collect(Collectors.toMap(f -> f.getId(), Function.identity()));
        var artistSongGroup = artistSongs.stream().collect(Collectors.groupingBy(f -> f.getArtistSongId().getSongs().getId()));
        var genresMap = genres.stream().collect(Collectors.toMap(f -> f.getId(), Function.identity()));
        for (SongResponseType songResponseType : songResponseTypes) {
            if (genresMap.containsKey(songResponseType.getGenres().getId())) {
                songResponseType.setGenres(genresMap.get(songResponseType.getGenres().getId()));
            }
            if (albumSongGroup.containsKey(songResponseType.getId())) {
                var albumSong = albumSongGroup.get(songResponseType.getId());
                var albumSongRes = albumSong.stream().map(f -> AlbumSongResponseType.builder().albums(albumConverter.convertToDTO(albumMap.getOrDefault(f.getAlbumSongId().getAlbums().getId(), null))).build()).collect(Collectors.toList());
                songResponseType.setAlbumSongs(albumSongRes);
            }
            if (artistSongGroup.containsKey(songResponseType.getId())) {
                var artistSong = artistSongGroup.get(songResponseType.getId());
                var artistSongRes = artistSong.stream().map(f -> ArtistSongResponseType.builder().artists(artistConverter.convertToDTO(artistMap.getOrDefault(f.getArtistSongId().getArtists().getId(), null))).build()).collect(Collectors.toList());
                songResponseType.setArtistSongs(artistSongRes);
            }
        }
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.SONG_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_TOP_LIST_KEY, allEntries = true),
    })
    @Override
    public SongResponseType saveSong(SongResponseType songResponseType) {
        Optional<Songs> songOptional = Optional.ofNullable(songRepository.findById(songResponseType.getId() == null ? 0 : songResponseType.getId()).orElse(null));
        Songs song = songConverter.convertToEntity(songResponseType);
        Songs songSave;
        SongResponseType songResponsetype;
        if (!songOptional.isPresent()) {
            songSave = songRepository.save(song);
            songResponsetype = songConverter.convertToDTO(songSave);
        } else {
            songSave = songOptional.get();
            if (null != song.getArtistSongs()) {
                if (song.getArtistSongs().size() != songSave.getArtistSongs().size()) {
                    if (songSave.getArtistSongs().size() > 0) {
                        artistSongRepository.deleteArtistSongs(songSave.getId());
                    }
                    songSave.setArtistSongs(song.getArtistSongs());
                }
            }
            songSave.setGenres(song.getGenres());
            songSave.setTitle(song.getTitle());
            if (null != song.getTimePlay()) {
                if (songSave.getTimePlay() != song.getTimePlay()) {
                    songSave.setTimePlay(song.getTimePlay());
                }
            }
            songSave.setLyrics(song.getLyrics());
            songSave.setDescription(song.getDescription());
            songSave.setCountListen(song.getCountListen());

            if (!StringUtils.isEmpty(song.getImage())) {
                songSave.setImage(song.getImage());
            }
            if (!StringUtils.isEmpty(song.getMediaUrl())) {
                songSave.setMediaUrl(song.getMediaUrl());
            }
            if (null != songResponseType.getAlbumSongs()) {
                if (songSave.getAlbumSongs().size() != song.getAlbumSongs().size()) {
                    songSave.setAlbumSongs(song.getAlbumSongs());
                }
            }
            if (songSave.getGenres() != song.getGenres()) {
                songSave.setGenres(song.getGenres());
            }
            songSave.setDownloadPermission(song.getDownloadPermission());
            songSave.setArtistSongs(song.getArtistSongs());
            songResponsetype = songConverter.convertToDTO(songRepository.save(songSave));
        }
        return songResponsetype;
    }

    @Cacheable(cacheNames = CACHE.SONG_TOP_LIST_KEY, key = "#result")
    @Override
    public List<SongResponseType> getTop15SongsPopular() {
        return List.of();
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.SONG_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_TOP_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_LIST_KEY, allEntries = true),
    })
    @Transactional
    @Override
    public boolean deleteListSong(List<Integer> listSongIds) {
        AtomicBoolean isSuccess = new AtomicBoolean(true);
        if (null != listSongIds) {
            listSongIds.forEach(id -> {
                Optional<Songs> songs = songRepository.findById(id);
                if (songs.isPresent()) {
                    songRepository.deleteById(id);
                    isSuccess.set(true);
                } else {
                    isSuccess.set(false);
                }
            });
        }
        return false;
    }

    @Override
    public void countTarget(CountModelRequest request) {
        switch (request.getTarget()) {
            case "album":
                albumRepository.updateCountListen(request.getId());
                break;
            case "song":
                songRepository.updateCountListen(request.getId());
                break;
            case "genres":
                genresRepository.updateCountListen(request.getId());
            case "artist":
                artistRepository.updateCountListen(request.getId());
        }
    }

    @Override
    public List<SongResponseType> trendingSongs() {
        return (List<SongResponseType>) this.paginationSongs(PaginationRequest.builder().order("desc")
                .field("countListen")
                .size(5)
                .page(1).build()).get("songs");
    }

    @Override
    public void enrichInfoSongs(List<SongResponseType> songs) {
        this.enrichInfos(songs);
    }

    private class CACHE {
        public static final String SONG_KEY = "song";
        public static final String SONG_LIST_KEY = "song-list";
        public static final String SONG_TOP_LIST_KEY = "song-top-list";

    }
}
