package com.example.userservice.controller;


import com.example.userservice.dto.UserDto;
import com.example.userservice.model.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
//user-service/"
@Slf4j
@RestController
@RequestMapping("/user-service")
public class UserController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private Greeting greeting;

    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/health_check")
    public String status(){
        return "It's working in User Service";
    }

    @GetMapping("/welcome")
    public String hello(){
        return greeting.getMessage();
    }

    @GetMapping("/message")
    public String message(@RequestHeader("user-request") String header){
        System.out.println(header);
        return "Hello world in user service " + header;
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){

        log.info("Server port = {} " , request.getServerPort());
        return String.format("check world in user service  PORT %s", port);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody UserDto user){

        ResponseUser responseUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v-> {
            result.add(modelMapper.map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){

        ResponseUser result = userService.getUserByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
