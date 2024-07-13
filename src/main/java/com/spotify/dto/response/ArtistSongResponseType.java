package com.spotify.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistSongResponseType implements Serializable {
    private ArtistResponseType artists;
    private SongResponseType songs;
}
