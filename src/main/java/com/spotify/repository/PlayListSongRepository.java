package com.spotify.repository;

import com.spotify.entities.PlayListSongId;
import com.spotify.entities.PlayListSongs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListSongRepository extends JpaRepository<PlayListSongs, PlayListSongId> {
}
