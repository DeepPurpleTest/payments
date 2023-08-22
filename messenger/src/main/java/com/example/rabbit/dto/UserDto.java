package com.example.rabbit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private String phoneNumber;
    private String email;
}
