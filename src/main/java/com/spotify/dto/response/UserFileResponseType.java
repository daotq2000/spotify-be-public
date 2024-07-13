package com.spotify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFileResponseType {
    private Integer id;
    private String fileName;
    private Float fileSize;
    private UserResponseType users;
}
