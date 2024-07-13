package com.spotify.repository;

import com.spotify.entities.ArtistAlbumId;
import com.spotify.entities.ArtistAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistAlbumRepository extends JpaRepository<ArtistAlbums,ArtistAlbumId> {

}
