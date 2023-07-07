package com.example.rabbit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class CardDto {
    String cardNumber;
    BigDecimal balance;
}