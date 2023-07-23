package com.example.payments.dto;

import jakarta.annotation.Nullable;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class AuthCredentialsDto {
    @Nullable
    private String phoneNumber;
    @Nullable
    private String password;
}
