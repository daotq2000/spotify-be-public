package com.spotify.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "songs")
@Entity

public class Songs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "time_play")
    private String timePlay;
    @Column(name = "download_permission")
    private Boolean downloadPermission;
    @Column(name = "media_url")
    private String mediaUrl;
    @Column(name = "lyrics", columnDefinition = "text")
    private String lyrics;
    @Column(name = "image")
    private String image;
    @Column(name = "description")
    private String description;
    @Column(name = "count_listen")
    private Long countListen;
    @Column(name = "genres_id")
    private Integer genres;
    @OneToMany(mappedBy = "artistSongId.songs", cascade = CascadeType.ALL)
    private List<ArtistSongs> artistSongs;
    @OneToMany(mappedBy = "songs", cascade = CascadeType.ALL)
    private List<HistoryListens> historyListens;
    @OneToMany(mappedBy = "albumSongId.songs", cascade = CascadeType.ALL)
    private List<AlbumSongs> albumSongs;

}
