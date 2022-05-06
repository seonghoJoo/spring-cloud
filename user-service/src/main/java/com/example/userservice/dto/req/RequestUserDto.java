package com.example.userservice.dto.req;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RequestUserDto {

    @NotNull
    private String email;
    private String name;
    private String pwd;

}
