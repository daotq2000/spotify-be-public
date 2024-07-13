package com.spotify.service.mapper;

import com.spotify.dto.response.SongResponseType;
import com.spotify.entities.Songs;
import org.springframework.stereotype.Component;

@Component("spring")
public interface SongMapper extends ModelMapper<Songs, SongResponseType> {
}
