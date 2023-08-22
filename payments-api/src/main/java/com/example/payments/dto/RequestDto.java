package com.example.payments.dto;

import com.example.payments.dto.identifiable.AbstractRequestIdentifiable;
import com.example.payments.entity.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class RequestDto implements AbstractRequestIdentifiable {
    @NotNull
    CardDto card;
    @NotNull
    RequestStatus status;
    @NotNull
    UserDto user;
    @NotNull
    UserDto admin;
}
