package com.example.payments.dto;

import com.example.payments.entity.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RequestDto {
    @NotNull
    CardDto card;
    @NotNull
    RequestStatus status;
    @NotNull
    UserDto user;
    @NotNull
    UserDto admin;
}
