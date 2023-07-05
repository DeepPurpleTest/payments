package com.example.payments.controller.handler;

import com.example.payments.util.exception.TransactionException;
import com.example.payments.util.response.IllegaleStateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionHandler {
    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<IllegaleStateResponse> handleException(TransactionException ex) {
        IllegaleStateResponse response = new IllegaleStateResponse(ex.getMessage(), System.currentTimeMillis());
        // return message with status of PAYMENT_REQUIRED - 402
        return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);
    }
}
