package com.example.payments.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class AuthCredentialsDto {
    @Nullable
    private String username;
    @Nullable
    private String password;
}
