package com.spotify.service.impl;

import com.spotify.dto.converter.AlbumConverter;
import com.spotify.dto.converter.GenresConverter;
import com.spotify.dto.converter.SongConverter;
import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.AlbumResponseType;
import com.spotify.dto.response.GenresResponseType;
import com.spotify.dto.response.SongResponseType;
import com.spotify.entities.Albums;
import com.spotify.entities.Genres;
import com.spotify.entities.Songs;
import com.spotify.exception.NotFoundEntityException;
import com.spotify.repository.GenresRepository;
import com.spotify.service.GenresService;
import com.spotify.service.SongService;
import com.spotify.ultils.Constraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenresServiceImpl implements GenresService {
    private final   GenresRepository genresRepository;
    private  final GenresConverter genresConverter;
    private  final SongService songService;
    @Autowired  private  SongConverter songConverter;
    @Autowired  private  AlbumConverter albumConverter;
    @Autowired
    public GenresServiceImpl(GenresRepository genresRepository, GenresConverter genresConverter, SongService songService) {
        this.genresRepository = genresRepository;
        this.genresConverter = genresConverter;
        this.songService = songService;
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.GENRES_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.GENRES_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_ALBUM_BY_GENRES_LIST_KEY, allEntries = true),
    })
    @Override
    public GenresResponseType save(GenresResponseType genresResponseType) {
        Genres genres = genresConverter.convertToEntity(genresResponseType);
        GenresResponseType genresResponseType1 = genresConverter.convertToDTO(genresRepository.save(genres));
        return genresResponseType1;
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.GENRES_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.GENRES_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_ALBUM_BY_GENRES_LIST_KEY, allEntries = true),
    })
    @Override
    public GenresResponseType update(GenresResponseType genresResponseType) {
        Genres genres = genresConverter.convertToEntity(genresResponseType);
        GenresResponseType genresResponseType1 = genresConverter.convertToDTO(genresRepository.save(genres));
        return genresResponseType1;    }
    @Cacheable(cacheNames = CACHE.GENRES_KEY,key = "#id")
    @Override
    public GenresResponseType findById(Integer id) {
        Optional<Genres> genres = genresRepository.findById(id);
        if (genres.isPresent()) {
            return genresConverter.convertToDTO(genres.get());
        }
        throw new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND);
    }
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE.GENRES_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.GENRES_LIST_KEY, allEntries = true),
            @CacheEvict(cacheNames = CACHE.SONG_ALBUM_BY_GENRES_LIST_KEY, allEntries = true),
    })
    @Transactional
    @Override
    public boolean delete(Integer id) {
        var entity = genresRepository.findById(id).orElseThrow(()->new NotFoundEntityException(Constraints.VALIDATE_NOT_FOUND));
        genresRepository.delete(entity);
        return true;
    }


    @Cacheable(cacheNames = CACHE.GENRES_KEY,key = "{#genresId,#paginationRequestAlbum.page,#paginationRequestAlbum.size,#paginationRequestAlbum.field,#paginationRequestAlbum.order,#paginationRequestAlbum.search,#paginationRequestSong.page,#paginationRequestSong.size,#paginationRequestSong.field,#paginationRequestSong.order,#paginationRequestSong.search}")
    @Override
    public Map<String, Object> getSongAndAlbumByGenresId(int genresId, PaginationRequest paginationRequestAlbum, PaginationRequest paginationRequestSong) {
        Pageable pageableAlbum = null;
        Pageable pageableSong = null;
        Map<String,Object> result = new HashMap<>();
        if (paginationRequestAlbum.getPage() > 0) {
            pageableAlbum = PageRequest.of(paginationRequestAlbum.getPage() - 1, paginationRequestAlbum.getSize());
        }
        if (paginationRequestAlbum.getOrder().equals("asc")) {
            pageableAlbum = PageRequest.of(paginationRequestAlbum.getPage() - 1, paginationRequestAlbum.getSize(), Sort.by(paginationRequestAlbum.getField()).ascending());
        }
        if (paginationRequestAlbum.getOrder().equals("desc")) {
            pageableAlbum = PageRequest.of(paginationRequestAlbum.getPage() - 1, paginationRequestAlbum.getSize(), Sort.by(paginationRequestAlbum.getField()).descending());
        }
        Page<Albums> albumsPage = genresRepository.getAlbumByGenresId(pageableAlbum,paginationRequestAlbum.getSearch(),genresId);
        if (paginationRequestSong.getPage() > 0) {
            pageableSong = PageRequest.of(paginationRequestSong.getPage() - 1, paginationRequestSong.getSize());
        }
        if (paginationRequestSong.getOrder().equals("asc")) {
            pageableSong = PageRequest.of(paginationRequestSong.getPage() - 1, paginationRequestSong.getSize(), Sort.by(paginationRequestSong.getField()).ascending());
        }
        if (paginationRequestSong.getOrder().equals("desc")) {
            pageableSong = PageRequest.of(paginationRequestSong.getPage() - 1, paginationRequestSong.getSize(), Sort.by(paginationRequestSong.getField()).descending());
        }
        Map<String,Object> albumKey = new HashMap<>();
        List<AlbumResponseType> albums = albumsPage.toList().stream().map(albums1 -> albumConverter.convertToDTO(albums1)).collect(Collectors.toList());
        albumKey.put("albums",albums);
        albumKey.put("totalPages",albumsPage.getTotalPages());
        albumKey.put("totalElements",albumsPage.getTotalElements());
        albumKey.put("currentPage",paginationRequestAlbum.getPage());
        Page<Songs> songsPage = genresRepository.getSongByGenresId(pageableSong,paginationRequestSong.getSearch(),genresId);
        Map<String,Object> songKey = new HashMap<>();
        List<SongResponseType> songs = songsPage.toList().stream().map(songs1 -> songConverter.convertToDTO(songs1)).collect(Collectors.toList());
        this.songService.enrichInfoSongs(songs);
        songKey.put("songs",songs);
        songKey.put("totalPages",songsPage.getTotalPages());
        songKey.put("totalElements",songsPage.getTotalElements());
        songKey.put("currentPage",paginationRequestSong.getPage());
        result.put("album",albumKey);
        result.put("song",songKey);
        return result;
    }

    @Cacheable(cacheNames = CACHE.GENRES_LIST_KEY,key = "{#request.page,#request.size,#request.field,#request.order,#request.search}")
    @Override
    public Map<String, Object> paginationGenres(PaginationRequest request) {
        Pageable pageable = null;
        if (request.getPage() > 0) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        }
        if (request.getOrder().equals("asc")) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getField()).ascending());
        }
        if (request.getOrder().equals("desc")) {
            pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getField()).descending());
        }
        Page<Genres> genresPage = genresRepository.paginationGenres(pageable, request.getSearch());
        List<GenresResponseType> genresResponseTypeList = genresPage.toList().stream().map(genres -> genresConverter.convertToDTO(genres)).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("genres", genresResponseTypeList);
        map.put("totalPages", genresPage.getTotalPages());
        map.put("totalElements", genresPage.getTotalElements());
        map.put("currentPage", request.getPage());
        return map;
    }
    private class CACHE{
        public static final String GENRES_KEY = "genres";
        public static final String GENRES_LIST_KEY = "genres-list";
        public static final String SONG_ALBUM_BY_GENRES_LIST_KEY = "song-album-list";
    }
}