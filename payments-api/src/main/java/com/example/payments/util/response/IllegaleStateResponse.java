package com.example.payments.util.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class IllegaleStateResponse {
    private final String message;
    private final Long timestamp;
}
