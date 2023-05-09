package com.ar.bankingonline.models.dtos;

import lombok.*;

@Builder //Permite crear objetos de forma parcial
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {

    private Integer id;

    private String username;

    private String password;

}
