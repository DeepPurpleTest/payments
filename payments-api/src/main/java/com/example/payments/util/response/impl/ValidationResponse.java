package com.example.payments.util.response.impl;

import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.response.ResponseWrapper;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class ValidationResponse implements ResponseWrapper<ValidationResponse, EntityValidationException> {
    private String message;
    private Map<String, String> errors;
    private String timestamp;

    @Override
    public ValidationResponse of(EntityValidationException ex) {
        this.message = ex.getMessage();
        this.errors = ex.getBindingResult().getAllErrors().stream()
                .map(er -> (FieldError) er)
                .collect(Collectors
                        .toMap(
                                FieldError::getField,
                                FieldError::getDefaultMessage)
                );
        this.timestamp = LocalDateTime.now().toString();
        return this;
    }
}
