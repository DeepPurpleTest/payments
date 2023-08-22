package com.example.payments.controller.handler;

import com.example.payments.util.exception.EntityDuplicateException;
import com.example.payments.util.exception.EntityNotFoundException;
import com.example.payments.util.response.impl.EntityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Global exception handler for Spring application
@RequiredArgsConstructor
public class EntityExceptionHandler {
    private final EntityResponse entityResponse;
    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<EntityResponse> handleException(EntityNotFoundException ex) {
        // return message with status of NOT_FOUND - 404
        return new ResponseEntity<>(entityResponse.of(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler // method for handle and what he handle in arguments
    public ResponseEntity<EntityResponse> handleException(EntityDuplicateException ex) {
        return new ResponseEntity<>(entityResponse.of(ex), HttpStatus.BAD_REQUEST);
    }
}
