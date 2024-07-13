package com.spotify.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenresResponseType implements Serializable {
    private Integer id;
    private String genresName;
    private String image;
    private List<AlbumResponseType> albums;
    private Long countListen;
}
