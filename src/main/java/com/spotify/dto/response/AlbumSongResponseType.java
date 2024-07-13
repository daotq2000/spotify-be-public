package com.spotify.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumSongResponseType implements Serializable {
    private AlbumResponseType albums;
    private SongResponseType songs;
}
