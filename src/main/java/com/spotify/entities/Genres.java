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
@Table(name = "genres")
@Entity
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "genres_name")
    private String genresName;
    @Column(name = "image", columnDefinition = "varchar(500)")
    private String image;
    @OneToMany(mappedBy = "genres")
    private List<Albums> albums;
    @Column(name = "count_listen")
    private Long countListen;
}
