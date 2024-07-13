package com.spotify.service;

import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.GenresResponseType;

import java.util.Map;

public interface GenresService extends BaseService<GenresResponseType, Integer> {
    Map<String, Object> getSongAndAlbumByGenresId(int genresId,PaginationRequest paginationRequestAlbum,PaginationRequest paginationRequestSong);
    Map<String, Object> paginationGenres(PaginationRequest paginationRequest);
}
