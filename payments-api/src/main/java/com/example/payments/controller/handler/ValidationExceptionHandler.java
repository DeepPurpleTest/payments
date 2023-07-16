package com.example.payments.controller.handler;

import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.response.impl.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final ValidationResponse validationResponse;
    @ExceptionHandler
    public ResponseEntity<ValidationResponse> handleException(EntityValidationException ex) {
        return new ResponseEntity<>(validationResponse.of(ex), HttpStatus.BAD_REQUEST);
    }
}
