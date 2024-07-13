package com.spotify.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class PlayListSongId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "songs_id")
    private Songs songs;
    @ManyToOne
    @JoinColumn(name = "play_list_id")
    private PlayLists playLists;
}
