package com.spotify.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistResponseType implements Serializable {
    private Integer id;
    private String fullName;
    private boolean gender;
    private LocalDate birthDay;
    private String description;
    private String countryActive;
    private String image;
    private List<ArtistSongResponseType> artistSongs;
    private List<ArtistAlbumResponseType> artistAlbums;
    private Long countListen;
}
