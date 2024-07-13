package com.spotify.service.impl;

import com.spotify.dto.converter.AlbumConverter;
import com.spotify.dto.converter.AlbumSongConverter;
import com.spotify.dto.converter.ArtistConverter;
import com.spotify.dto.converter.SongConverter;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.AlbumResponseType;
import com.spotify.dto.response.AlbumSongResponseType;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.entities.AlbumSongs;
import com.spotify.entities.Albums;
import com.spotify.exception.NotFoundEntityException;
import com.spotify.repository.*;
import com.spotify.service.AlbumService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumConverter albumConverter;
    private final SongRepository songRepository;
    private final SongConverter songConverter;
    private final AlbumSongRepository albumSongRepository;
    private final AlbumSongConverter albumSongConverter;
    private final ArtistSongRepository artistSongRepository;
    private final ArtistRepository artistRepository;
    private final ArtistConverter artistConverter;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumConverter albumConverter, SongRepository songRepository, SongConverter songConverter, AlbumSongRepository albumSongRepository, AlbumSongConverter albumSongConverter, ArtistSongRepository artistSongRepository, ArtistRepository artistRepository, ArtistConverter artistConverter) {
        this.albumRepository = albumRepository;
        this.albumConverter = albumConverter;
        this.songRepository = songRepository;
        this.songConverter = songConverter;
        this.albumSongRepository = albumSongRepository;
        this.albumSongConverter = albumSongConverter;
        this.artistSongRepository = artistSongRepository;
        this.artistRepository = artistRepository;
        this.artistConverter = artistConverter;
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.ALBUM_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_SONG_LIST_KEY, allEntries = true),
    })
    @Override
    public AlbumResponseType save(AlbumResponseType albumResponseType) throws Exception {
        AlbumResponseType response = null;
        if (StringUtils.isEmpty(albumResponseType.getAlbumName())) {
            throw new IllegalArgumentException("Album name is require");
        }
        Albums albums = albumConverter.convertToEntity(albumResponseType);
        Albums albumSave = albumRepository.save(albums);
        if (null != albumSave) {
            response = albumConverter.convertToAllDependency(albumSave);
        }
        return response;
    }

    @Override
    public AlbumResponseType update(AlbumResponseType albumResponseType) {
        AlbumResponseType response = null;
        if (StringUtils.isEmpty(albumResponseType.getAlbumName())) {
            throw new IllegalArgumentException("Album name is require");
        }
        Albums albums = albumConverter.convertToEntity(albumResponseType);
        Albums albumSave = albumRepository.save(albums);
        if (null != albumSave) {
            response = albumConverter.convertToAllDependency(albumSave);
        }
        return response;
    }
    @Cacheable(cacheNames = CACHE.ALBUM_KEY,key = "#id")
    @Override
    public AlbumResponseType findById(Integer id) {
        Optional<Albums> album = albumRepository.findById(id);
        if (album.isPresent()) {
            var albumTarget =  albumConverter.convertToDTO(album.get());
            this.enrichInfos(List.of(albumTarget), false);
            return albumTarget;
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }

    private void enrichInfos(List<AlbumResponseType> albumTarget, boolean isList) {
        if(isList){
            return;
        }
        var albumIds = albumTarget.stream().map(AlbumResponseType::getId).collect(Collectors.toList());
        var albumSongs = albumSongRepository.findAllByAlbumIds(albumIds);
        var albumSongGroup = albumSongs.stream().collect(Collectors.groupingBy(f->f.getAlbumSongId().getAlbums().getId()));
        var songs = albumSongs.stream().map(f->f.getAlbumSongId().getSongs().getId()).collect(Collectors.toList());
        var songMap = songRepository.findAllById(songs).stream().collect(Collectors.toMap(f->f.getId(), Function.identity()));
        var artistSongs = artistSongRepository.findBySongIds(albumSongs.stream().map(f->f.getAlbumSongId().getSongs().getId()).collect(Collectors.toList()));
        var artisSongMap = artistSongs.stream().collect(Collectors.groupingBy(f->f.getArtistSongId().getSongs().getId()));
        var artistIds = artistSongs.stream().map(f->f.getArtistSongId().getArtists().getId()).collect(Collectors.toList());
        var artistMap = artistRepository.findAllById(artistIds).stream().collect(Collectors.toMap(f->f.getId(),Function.identity()));
        albumTarget.forEach(f->{
            if(albumSongGroup.containsKey(f.getId())) {

                var albumSongList = albumSongGroup.get(f.getId())
                        .stream().map(al ->{
                            var song = songConverter.convertToDTO(songMap.getOrDefault(al.getAlbumSongId().getSongs().getId(),null));
                            if(artisSongMap.containsKey(song.getId())) {
                                var artisSongTargets = artisSongMap.get(song.getId());
                                song.setArtistSongs(artisSongTargets.stream().map(sa -> ArtistSongResponseType.builder()
                                        .artists(artistConverter.convertToDTO(artistMap.getOrDefault(sa.getArtistSongId().getArtists().getId(),null)))
                                        .build()).collect(Collectors.toList()));
                            }
                            return AlbumSongResponseType.builder()
                                    .songs(song).build();
                        })
                        .collect(Collectors.toList());
                f.setAlbumSongs(albumSongList);
            }
        });
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.ALBUM_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_SONG_LIST_KEY, allEntries = true),
    })
    @Override
    public boolean delete(Integer id) {
        Optional<Albums> album = albumRepository.findById(id);
        if (album.isPresent()) {
            albumRepository.deleteById(id);
            return true;
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }

    @Cacheable(cacheNames = CACHE.ALBUM_LIST_KEY,key = "{#request.page,#request.size,#request.field,#request.order,#request.search}")
    @Override
    public Map<String, Object> paginationAlbum(PaginationRequest request) {
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
        Page<Albums> albumsPage = albumRepository.paginationAlbum(pageable, request.getSearch());
        List<AlbumResponseType> albumResponseTypes = albumsPage.toList().stream().map(albums -> albumConverter.convertToDTO(albums)).collect(Collectors.toList());
        this.enrichInfos(albumResponseTypes,true);
        result.put("albums", albumResponseTypes);
        result.put("totalPages", albumsPage.getTotalPages());
        result.put("totalElements", albumsPage.getTotalElements());
        result.put("currentPage", request.getPage());
        return result;
    }

    @Cacheable(cacheNames = CACHE.ALBUM_SONG_LIST_KEY,key = "#albumId")
    @Override
    public List<AlbumSongResponseType> getListSongByAlbumId(int albumId) {
        List<AlbumSongs> albumSongs = albumSongRepository.getAllByAlbumId(albumId);
        List<AlbumSongResponseType> albumResponseTypes = albumSongs.stream().map(albumSongs1 -> albumSongConverter.convertToDTO(albumSongs1)).collect(Collectors.toList());
        return albumResponseTypes;
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.ALBUM_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_SONG_LIST_KEY, allEntries = true),
    })
    public AlbumResponseType updateAlbum(AlbumResponseType albumResponseType, Integer id) throws Exception {
        Albums albums = null;
        Optional<Albums> albumsOptional = Optional.ofNullable(albumRepository.findById(id).orElse(null));
        Albums albumsUpdate = albumConverter.convertToEntity(albumResponseType);
        if (albumsOptional.isPresent()) {
            albumRepository.deleteByAlbumSongs(albumsOptional.get().getId());
            albums = albumsOptional.get();
            albums.setArtistAlbums(albumsUpdate.getArtistAlbums());
            albums.setGenres(albumsUpdate.getGenres());
            albums.setAlbumName(albumsUpdate.getAlbumName());
            albums.setTotalListen(albumsUpdate.getTotalListen());
            albums.setImage(albumsUpdate.getImage());
            albums.setArtistAlbums(albumsUpdate.getArtistAlbums());
            albums.setDownloadPermission(albumsUpdate.isDownloadPermission());
            albums.setAlbumSongs(albumsUpdate.getAlbumSongs());
            Albums albumsSave = albumRepository.save(albums);
            return albumConverter.convertToAllDependency(albumsSave);
        }
        return null;
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.ALBUM_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.ALBUM_SONG_LIST_KEY, allEntries = true),
    })
    @Transactional
    @Override
    public Boolean deleteListAlbumId(List<Integer> albumIds) {
        AtomicBoolean isSuccess = new AtomicBoolean(true);
        albumIds.forEach(integer -> {
            Optional<Albums> albums = albumRepository.findById(integer);
            if (albums.isPresent()) {
                albumRepository.delete(albums.get());
                isSuccess.set(true);
            } else {
                isSuccess.set(false);
            }
        });
        return isSuccess.get();
    }
    private class CACHE{
        public static final String ALBUM_KEY = "album";
        public static final String ALBUM_LIST_KEY = "album-list";
        public static final String ALBUM_SONG_LIST_KEY = "artist-song-list";
    }
}
