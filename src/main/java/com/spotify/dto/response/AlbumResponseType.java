package com.spotify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponseType implements Serializable {
    private Integer id;
    private String albumName;
    private Date releaseDate;
    private Float albumTimeLength;
    private Integer totalListen;
    private String image;
    private boolean downloadPermission;
    private GenresResponseType genres;
    private List<ArtistAlbumResponseType> artistAlbums;
    private Long countListen;
    private List<AlbumSongResponseType> albumSongs;
}
