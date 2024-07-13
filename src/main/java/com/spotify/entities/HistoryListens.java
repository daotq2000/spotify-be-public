package com.spotify.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history_listens")
@Entity

public class HistoryListens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "songs_id")
    private Songs songs;
    @Column(name = "count_listen")
    private int countListen;
    @Column(name = "date")
    private LocalDate date;
}
