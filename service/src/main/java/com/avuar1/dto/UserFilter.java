package com.avuar1.dto;

import lombok.*;

@Value
@Builder
public class UserFilter {

    String firstName;
    String lastName;
}
