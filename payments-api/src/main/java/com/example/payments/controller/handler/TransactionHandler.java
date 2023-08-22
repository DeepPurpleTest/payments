package com.example.payments.controller.handler;

import com.example.payments.util.exception.TransactionException;
import com.example.payments.util.response.impl.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class TransactionHandler {
    private final TransactionResponse transactionResponse;
    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<TransactionResponse> handleException(TransactionException ex) {
        // return message with status of PAYMENT_REQUIRED - 402
        return new ResponseEntity<>(transactionResponse.of(ex), HttpStatus.PAYMENT_REQUIRED);
    }
}
