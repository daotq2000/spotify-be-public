package com.spotify.service.impl;

import com.spotify.dto.response.ArtistAlbumResponseType;
import com.spotify.entities.ArtistAlbumId;
import com.spotify.service.ArtistAlbumService;
import org.springframework.stereotype.Service;

@Service
public class ArtistAlbumServiceImpl implements ArtistAlbumService {
    @Override
    public ArtistAlbumResponseType save(ArtistAlbumResponseType artistAlbumResponseType) {
        return null;
    }

    @Override
    public ArtistAlbumResponseType update(ArtistAlbumResponseType artistAlbumResponseType) {
        return null;
    }

    @Override
    public ArtistAlbumResponseType findById(ArtistAlbumId id) {
        return null;
    }

    @Override
    public boolean delete(ArtistAlbumId id) {
        return false;
    }
}
