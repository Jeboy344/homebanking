package com.ar.bankingonline.api.dtos;

import lombok.*;

@Data
public class UserDto {

    public UserDto(){}

    private Integer id;

    private String username;

    private String password;

}
