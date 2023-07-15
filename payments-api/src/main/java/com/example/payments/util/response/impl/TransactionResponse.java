package com.example.payments.util.response.impl;

import com.example.payments.util.response.ResponseWrapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class TransactionResponse implements ResponseWrapper<TransactionResponse, RuntimeException> {
    private String message;
    private String timestamp;
    @Override
    public TransactionResponse of(RuntimeException ex) {
        this.message = ex.getMessage();
        this.timestamp = LocalDateTime.now().toString();
        return this;
    }
}
