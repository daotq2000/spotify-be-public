package com.spotify.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class PlayListSongResponseType {
    private SongResponseType songs;
    private PlayListResponseType playLists;
}
