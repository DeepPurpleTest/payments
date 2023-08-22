package com.example.payments.controller.handler;

import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccessExceptionHandler {
    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<String> handleException(AccessException ex) {
        // return message with status of UNAUTHORIZED - 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied");
    }

}
