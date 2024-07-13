package com.spotify.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryListenResponseType implements Serializable {
    private Integer id;
    private UserResponseType users;
    private SongResponseType songs;
    private int countListen;
    private LocalDate date;
}
