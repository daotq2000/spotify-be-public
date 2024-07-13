package com.spotify.service.impl;

import com.spotify.dto.converter.UserConverter;
import com.spotify.dto.response.UserResponseType;
import com.spotify.entities.Users;
import com.spotify.repository.RoleRepository;
import com.spotify.repository.UserRepository;
import com.spotify.service.UserService;
import com.spotify.ultils.Constraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserConverter userConverter;
    private RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.roleRepository = roleRepository;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserResponseType saveUser(UserResponseType userResponseType) {
        Users user = userConverter.convertToEntity(userResponseType);
        Optional<Users> usersOptional = Optional.ofNullable(userRepository.findById((user.getId() == null?0: user.getId())).orElse(null));
        UserResponseType response;
        if(null != userResponseType.getId() && usersOptional.get() != null){
            Users userSave = usersOptional.get();
            if(userSave.getAvatar() != user.getAvatar()){
                userSave.setAvatar(user.getAvatar());
            }
            if(userSave.getEmail() != user.getEmail()){
                userSave.setEmail(user.getEmail());
            }
            if(userSave.getActiveStatus() != user.getActiveStatus()){
                userSave.setActiveStatus(user.getActiveStatus());
            }
            if(userSave.getBirthDay() != user.getBirthDay()){
                userSave.setBirthDay(user.getBirthDay());
            }
            if(user.getPassword() != null){
                if(bCryptPasswordEncoder.matches(userSave.getPassword(),bCryptPasswordEncoder.encode(user.getPassword()))){
                    userSave.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                }
            }
            if(userSave.getPhoneNumber() != user.getPhoneNumber()){
                userSave.setPhoneNumber(user.getPhoneNumber());
            }
            if(userSave.getLastName() != user.getLastName()){
                userSave.setLastName(user.getLastName());
            }
            if(userSave.getFirstName() != user.getFirstName()){
                userSave.setFirstName(user.getFirstName());
            }
            Users userUpdate = userRepository.save(userSave);
            response = userConverter.convertToDTO(userUpdate);
        }else {
            user.setRoles(roleRepository.getById(Constraints.ROLE_USER_ID));
            Users userSave = userRepository.save(user);
            response = userConverter.convertToDTO(userSave);
        }
        return response;
    }
}
