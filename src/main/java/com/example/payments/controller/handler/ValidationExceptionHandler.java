package com.example.payments.controller.handler;

import com.example.payments.util.exception.ValidationException;
import com.example.payments.util.response.IllegaleStateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<IllegaleStateResponse> handle(ValidationException ex) {
        IllegaleStateResponse response = new IllegaleStateResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
