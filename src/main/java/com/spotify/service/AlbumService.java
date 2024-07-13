package com.spotify.service;

import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.AlbumResponseType;
import com.spotify.dto.response.AlbumSongResponseType;

import java.util.List;
import java.util.Map;

public interface AlbumService extends BaseService<AlbumResponseType, Integer> {
    Map<String, Object> paginationAlbum(PaginationRequest request);

    List<AlbumSongResponseType> getListSongByAlbumId(int albumId);

    AlbumResponseType updateAlbum(AlbumResponseType albumResponseType, Integer id) throws Exception;

    Boolean deleteListAlbumId(List<Integer> albumIds);
}
