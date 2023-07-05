package com.example.payments.controller.handler;

import com.example.payments.util.response.IllegaleStateResponse;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccessExceptionHandler {
    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<IllegaleStateResponse> handleException(AccessException ex) {
        IllegaleStateResponse response = new IllegaleStateResponse(ex.getMessage(), System.currentTimeMillis());
        // return message with status of UNAUTHORIZED - 401
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
