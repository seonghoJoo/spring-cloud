package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.UserEntity;
import com.example.userservice.vo.ResponseUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public ResponseUser createUser(UserDto userDto);

    public ResponseUser getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
