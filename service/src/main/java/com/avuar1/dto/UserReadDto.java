package com.avuar1.dto;

import com.avuar1.entity.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
}