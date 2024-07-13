package com.spotify.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artists")
@Entity
public class Artists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "country_active")
    private String countryActive;
    @Column(name = "image", columnDefinition = "varchar(500)")
    private String image;
    @OneToMany(mappedBy = "artistSongId.artists")
    private List<ArtistSongs> artistSongs;
    @OneToMany(mappedBy = "artistAlbumId.artists")
    private List<ArtistAlbums> artistAlbums;
    @Column(name = "count_listen")
    private Long countListen;
}
