package com.example.payments.dto;

import com.example.payments.entity.RequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RequestDto {
    CardDto card;
    RequestStatus status;
    UserDto user;
    UserDto admin;
}
