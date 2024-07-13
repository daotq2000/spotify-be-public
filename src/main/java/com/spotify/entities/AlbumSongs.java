package com.spotify.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "album_songs")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumSongs {
    @EmbeddedId
    private AlbumSongId albumSongId;
}
