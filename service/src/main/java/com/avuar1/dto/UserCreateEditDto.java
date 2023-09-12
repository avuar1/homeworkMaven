package com.avuar1.dto;

import com.avuar1.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
}
