package com.spotify.repository;

import com.spotify.entities.AlbumSongs;
import com.spotify.entities.Albums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Albums, Integer> {
    @Query(value = "SELECT A FROM Albums A where (A.albumName like %:search% ) ")
    Page<Albums> paginationAlbum(Pageable pageable, String search);

    @Query(value = "SELECT P from AlbumSongs P where P.albumSongId.albums.id =:albumId")
    List<AlbumSongs> getAlbumsSongByAlbumId(Integer albumId);

    @Query(value = "DELETE FROM AlbumSongs WHERE albumSongId.albums.id =:albumId")
    @Modifying
    int deleteByAlbumSongs(int albumId);

    @Modifying
    @Query(value = "UPDATE Albums a set a.countListen = a.countListen+ 1 where a.id =:albumId")
    void updateCountListen(Integer albumId);
}
