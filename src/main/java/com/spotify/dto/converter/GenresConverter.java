package com.spotify.dto.converter;

import com.spotify.dto.response.GenresResponseType;
import com.spotify.entities.Genres;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenresConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public GenresResponseType convertToDTO(Genres genres) {
        GenresResponseType genresResponseType = new GenresResponseType();
        genresResponseType.setImage(genres.getImage());
        genresResponseType.setGenresName(genres.getGenresName());
        genresResponseType.setId(genres.getId());
        genresResponseType.setCountListen(genres.getCountListen());
        return genresResponseType;
    }

    public Genres convertToEntity(GenresResponseType genresResponseType) {
        Genres genres = modelMapper.map(genresResponseType, Genres.class);
        return genres;
    }
}
