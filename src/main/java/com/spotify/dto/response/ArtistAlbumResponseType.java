package com.spotify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistAlbumResponseType implements Serializable {
    private AlbumResponseType albums;
    private ArtistResponseType artists;
}
