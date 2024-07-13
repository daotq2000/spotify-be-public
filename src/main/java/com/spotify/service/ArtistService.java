package com.spotify.service;

import com.spotify.dto.request.PaginationRequest;
import com.spotify.dto.response.ArtistResponseType;
import com.spotify.dto.response.ArtistSongResponseType;

import java.util.List;
import java.util.Map;

public interface ArtistService extends BaseService<ArtistResponseType, Integer> {
    Map<String, Object> paginationArtist(PaginationRequest request);

    List<ArtistSongResponseType> getListArtistSongsByArtistId(int artistId);
}
