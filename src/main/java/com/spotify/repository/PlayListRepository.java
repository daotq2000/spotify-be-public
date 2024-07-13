package com.spotify.repository;

import com.spotify.entities.PlayLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayLists, Integer> {
}
