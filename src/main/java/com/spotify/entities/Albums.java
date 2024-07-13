package com.spotify.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "albums")
@Entity
@ToString
public class Albums {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "album_name")
    private String albumName;
    @Column(name = "release_date")
    private Date releaseDate;
    @Column(name = "album_time_length")
    private Float albumTimeLength;
    @Column(name = "total_listen")
    private int totalListen;
    @Column(name = "image", columnDefinition = "varchar(500)")
    private String image;
    @Column(name = "download_permission", columnDefinition = "tinyint(1)")
    private boolean downloadPermission;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genres_id")
    private Genres genres;
    @OneToMany(mappedBy = "artistAlbumId.albums", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArtistAlbums> artistAlbums;
    @OneToMany(mappedBy = "albumSongId.albums", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AlbumSongs> albumSongs;
    @Column(name = "count_listen")
    private Long countListen;
}
