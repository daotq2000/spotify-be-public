package com.spotify.repository;

import com.spotify.entities.Songs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Songs, Integer> {
    @Query(value = "select s from Songs s where (coalesce(:search,null) is null or lower(s.lyrics) like %:search% or lower(s.description) like %:search% or lower( s.title) like %:search% ) ")
    Page<Songs> paginationSongs(Pageable pageable, String search);

    @Query(value = "SELECT * FROM `songs` order by count_listen desc limit 15", nativeQuery = true)
    List<Songs> getTop15SongPopular();
    @Query(value = "UPDATE Songs a set a.countListen = a.countListen+ 1 where a.id =:id")
    @Modifying
    void updateCountListen(Integer id);
}
