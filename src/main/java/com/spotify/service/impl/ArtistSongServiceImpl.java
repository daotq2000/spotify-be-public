package com.spotify.service.impl;

import com.spotify.dto.converter.ArtistConverter;
import com.spotify.dto.response.ArtistSongResponseType;
import com.spotify.repository.ArtistRepository;
import com.spotify.service.ArtistSongService;
import org.springframework.stereotype.Service;

@Service
public class ArtistSongServiceImpl implements ArtistSongService {
    private ArtistRepository artistRepository;
    private ArtistConverter artistConverter;

    @Override
    public ArtistSongResponseType save(ArtistSongResponseType artistSongResponseType) {
//        Optional
        return null;
    }

    @Override
    public ArtistSongResponseType update(ArtistSongResponseType artistSongResponseType) {
        return null;
    }

    @Override
    public ArtistSongResponseType findById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
