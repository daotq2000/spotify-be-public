package com.spotify.service.impl;

import com.spotify.dto.converter.ArtistConverter;
import com.spotify.dto.converter.ArtistSongConverter;
import com.spotify.dto.converter.SongConverter;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.ArtistResponseType;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.entities.ArtistSongs;
import com.spotify.entities.Artists;
import com.spotify.exception.NotFoundEntityException;
import com.spotify.repository.ArtistRepository;
import com.spotify.repository.ArtistSongRepository;
import com.spotify.repository.SongRepository;
import com.spotify.service.ArtistService;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistConverter artistConverter;
    private final ArtistSongConverter artistSongConverter;
    private final SongRepository songRepository;
    private final ArtistSongRepository artistSongRepository;
    private final SongConverter songConverter;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistConverter artistConverter, ArtistSongConverter artistSongConverter, SongRepository songRepository, ArtistSongRepository artistSongRepository, SongConverter songConverter, SongConverter songConverter1) {
        this.artistRepository = artistRepository;
        this.artistConverter = artistConverter;
        this.artistSongConverter = artistSongConverter;
        this.songRepository = songRepository;
        this.artistSongRepository = artistSongRepository;
        this.songConverter = songConverter1;
    }
    @Caching(evict = {
            @CacheEvict(cacheNames =CACHE.ARTIST_KEY, allEntries = true),
            @CacheEvict(cacheNames =CACHE.ARTIST_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames =CACHE.ARTIST_SONG_LIST_KEY, allEntries = true),
    })
    @Override
    public ArtistResponseType save(ArtistResponseType artistResponseType) {
        if (StringUtils.isEmpty(artistResponseType.getFullName())) {
            throw new IllegalArgumentException("Name of Artist is require field");
        }
        if (null == artistResponseType.getBirthDay()) {
            throw new IllegalArgumentException("Birth day of Artist is require field");
        }
        if (StringUtils.isEmpty(artistResponseType.getCountryActive())) {
            throw new IllegalArgumentException("Country activities is require field");
        }
        Artists artists = artistConverter.convertToEntity(artistResponseType);
        Artists artistsSave;
        ArtistResponseType artistResponse;
        Optional<Artists> optionalArtist = Optional.ofNullable(artistRepository.findById(artistResponseType.getId() == null?0:artistResponseType.getId()).orElse(null));
        if (!optionalArtist.isPresent()) {
            artistsSave = artistRepository.save(artists);
            artistResponse = artistConverter.convertToDTO(artistsSave);
        } else {
            artistsSave = optionalArtist.get();
            if (null != artists.getImage()) {
                artistsSave.setImage(artists.getImage());
            }
            artistsSave.setBirthDay(artists.getBirthDay());
            artistsSave.setCountryActive(artists.getCountryActive());
            artistsSave.setFullName(artists.getFullName());
            artistsSave.setDescription(artists.getDescription());
            artistsSave.setGender(artists.isGender());
            artistResponse = artistConverter.convertToDTO(artistRepository.save(artistsSave));
        }
        return artistResponse;
    }

    @Override
    public ArtistResponseType update(ArtistResponseType artistResponseType) {
        return null;
    }

    @Cacheable(cacheNames = CACHE.ARTIST_KEY,key = "#id")
    @Override
    public ArtistResponseType findById(Integer id) {
        Optional<Artists> artists = artistRepository.findById(id);
        if (artists.isPresent()) {
            return artistConverter.convertAllDependencies(artists.get());
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }
    @Caching(evict = {
            @CacheEvict(cacheNames =CACHE.ARTIST_KEY, allEntries = true),
            @CacheEvict(cacheNames =CACHE.ARTIST_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames =CACHE.ARTIST_SONG_LIST_KEY, allEntries = true),
    })
    @Override
    public boolean delete(Integer id) {
        Optional<Artists> artists = artistRepository.findById(id);
        if (artists.isPresent()) {
            artistRepository.deleteById(id);
            return true;
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }
    @Cacheable(cacheNames = CACHE.ARTIST_LIST_KEY,key = "{#request.page,#request.size,#request.field,#request.order,#request.search}")
    @Override
    public Map<String, Object> paginationArtist(PaginationRequest request) {
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
        Page<Artists> artistsPage = artistRepository.paginationArtist(pageable, request.getSearch());
        List<ArtistResponseType> albumResponseTypes = artistsPage.toList().stream().map(artists -> artistConverter.convertToDTO(artists)).collect(Collectors.toList());
        result.put("artists", albumResponseTypes);
        result.put("totalPages", artistsPage.getTotalPages());
        result.put("totalElements", artistsPage.getTotalElements());
        result.put("currentPage", request.getPage());
        return result;
    }
    @Cacheable(cacheNames = CACHE.ARTIST_SONG_LIST_KEY,key = "#artistId")
    @Override
    public List<ArtistSongResponseType> getListArtistSongsByArtistId(int artistId) {
        List<ArtistSongs> artistSongs = artistRepository.getAllByArtistId(artistId);
        var songs = songRepository.findAllById(artistSongs.stream().map(f->f.getArtistSongId().getSongs().getId()).collect(Collectors.toList()));
        var songMap = songs.stream().collect(Collectors.toMap(f->f.getId(), Function.identity()));
        var artistSongEntities = artistSongRepository.findBySongIds(new ArrayList<>(songMap.keySet()));
        var artistSongGroup = artistSongEntities.stream().collect(Collectors.groupingBy(f->f.getArtistSongId().getSongs().getId()));
        var artistEntities = artistRepository.findAllById(artistSongEntities.stream().map(f->f.getArtistSongId().getArtists().getId()).collect(Collectors.toList()));
        var artistMap = artistEntities.stream().collect(Collectors.toMap(f->f.getId(), Function.identity()));
        List<ArtistSongResponseType> response = new ArrayList<>();
        if (null != artistSongs) {
            for (ArtistSongs artistSong : artistSongs) {
                if(songMap.containsKey(artistSong.getArtistSongId().getSongs().getId())) {
                    var song = songConverter.convertToDTO(songMap.getOrDefault(artistSong.getArtistSongId().getSongs().getId(),null));
                    if(artistSongGroup.containsKey(song.getId())) {
                        var res = artistSongGroup.get(song.getId()).stream().map(f->ArtistSongResponseType.builder()
                                .artists(artistConverter.convertToDTO(artistMap.getOrDefault(artistSong.getArtistSongId().getArtists().getId(),null)))
                                .build()).collect(Collectors.toList());
                        song.setArtistSongs(res);
                    }
                    var artistSongResponseType = ArtistSongResponseType.builder()
                            .artists(artistConverter.convertToDTO(artistMap.getOrDefault(artistSong.getArtistSongId().getArtists().getId(),null)))
                            .songs(song)
                            .build();
                    response.add(artistSongResponseType);
                }
            }
        }
        return response;
    }
    private class CACHE{
        public static final String ARTIST_KEY = "artist";
        public static final String ARTIST_LIST_KEY = "artist-list";
        public static final String ARTIST_SONG_LIST_KEY = "artist-song-list";
    }
}
