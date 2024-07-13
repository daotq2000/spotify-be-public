package com.spotify.service;

import com.spotify.dto.response.UserResponseType;

public interface UserService {
    UserResponseType saveUser(UserResponseType userResponseType);
}
