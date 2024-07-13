package com.spotify.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ArtistAlbumId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albums_id")
    private Albums albums;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artists_id")
    private Artists artists;
}
