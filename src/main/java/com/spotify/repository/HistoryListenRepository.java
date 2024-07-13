package com.spotify.repository;

import com.spotify.entities.HistoryListens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryListenRepository extends JpaRepository<HistoryListens,Integer> {
}
