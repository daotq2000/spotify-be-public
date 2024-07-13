package com.spotify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayListResponseType {
    private Integer id;
    private String playlistName;
    private int favoriteOrder;
    private Long countListen;
    private UserResponseType users;
}
