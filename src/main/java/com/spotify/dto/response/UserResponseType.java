package com.spotify.dto.response;

import com.spotify.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseType {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String avatar;
    private boolean gender;
    private LocalDate birthDay;
    private int activeStatus;
    private String email;
    private String phoneNumber;
    private Roles roles;
    private List<UserFileResponseType> userFiles;
    private List<HistoryListenResponseType> historyListens;
    private List<PlayListResponseType> playLists;
}
