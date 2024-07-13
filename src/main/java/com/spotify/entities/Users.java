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
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    @Column(name = "active_status")
    private int activeStatus;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "avatar")
    private String avatar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id")
    private Roles roles;
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<UserFiles> userFiles;
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<HistoryListens> historyListens;
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<PlayLists> playLists;
}
