package com.avuar1.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    String firstName;
    String lastName;
}
