package com.example.payments.controller.handler;

import com.example.payments.util.exception.UserNotFoundException;
import com.example.payments.util.response.IllegaleStateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Global exception handler for Spring application
public class EntityExceptionHandler {
    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<IllegaleStateResponse> handleException(UserNotFoundException ex) {
        IllegaleStateResponse response = new IllegaleStateResponse(ex.getMessage(), System.currentTimeMillis());
        // return message with status of NOT_FOUND - 404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
