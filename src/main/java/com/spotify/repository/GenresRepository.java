package com.spotify.repository;

import com.spotify.entities.Albums;
import com.spotify.entities.Genres;
import com.spotify.entities.Songs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Integer> {
    @Query(value = "SELECT g FROM Genres g where g.genresName like %:search%")
    Page<Genres> paginationGenres(Pageable pageable, String search);
    @Query(value = "select a from Albums a where a.genres.id =:genresId and a.albumName like %:search%")
    Page<Albums> getAlbumByGenresId(Pageable pageable,String search,int genresId);
    @Query(value = "select s from Songs s where s.genres.id =:genresId and s.title like %:search%")
    Page<Songs> getSongByGenresId(Pageable pageable, String search, int genresId);
    @Query(value = "select distinct g from Genres g inner join Songs s on g.id = s.genres.id where s.id in :songIds")
    List<Genres> findAllBySongIds(List<Integer> songIds);
    @Query(value = "UPDATE Genres a set a.countListen = a.countListen + 1 where a.id =:id")
    @Modifying
    void updateCountListen(Integer id);
}
