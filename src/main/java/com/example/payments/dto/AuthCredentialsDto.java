package com.example.payments.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AuthCredentialsDto {
    @Nullable
    private String phoneNumber;
    @Nullable
    private String password;
}
