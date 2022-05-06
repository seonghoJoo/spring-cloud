package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.req.RequestUserDto;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd()
        ,true,true ,true,true,
                new ArrayList<>());
    }

    @Override
    public ResponseUser createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        // pwd encode
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, ResponseUser.class);
    }

    @Override
    public ResponseUser getUserByUserId(String userId) {
        UserEntity entity = userRepository.findByUserId(userId);

        if(entity == null){
            throw new UsernameNotFoundException("User not founded");
        }

        ResponseUser responseUser = modelMapper.map(entity, ResponseUser.class);

        List<ResponseOrder> oreders = new ArrayList();
        responseUser.setOrders(oreders);
        return responseUser;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
