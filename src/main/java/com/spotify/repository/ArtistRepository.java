package com.spotify.repository;

import com.spotify.entities.ArtistSongs;
import com.spotify.entities.Artists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artists, Integer> {
    @Query(value = "SELECT A from Artists A where A.fullName like %:search%")
    Page<Artists> paginationArtist(Pageable pageable, String search);

    @Query(value = "SELECT A from ArtistSongs A where A.artistSongId.artists.id =:artistId ")
    List<ArtistSongs> getAllByArtistId(int artistId);
    @Query(value = "UPDATE Artists a set a.countListen = a.countListen+ 1 where a.id =:id")
    @Modifying
    void updateCountListen(Integer id);
}
