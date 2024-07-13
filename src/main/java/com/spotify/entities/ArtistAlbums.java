package com.spotify.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "artist_albums")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistAlbums {
    @EmbeddedId
    private ArtistAlbumId artistAlbumId;
}
