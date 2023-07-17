package com.example.payments.util.response.impl;

import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.response.ObjectError;
import com.example.payments.util.response.ResponseWrapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
public class ValidationResponse implements ResponseWrapper<ValidationResponse, EntityValidationException> {
    private String message;
    private String timestamp;
    private List<ObjectError> errors;

    @Override
    public ValidationResponse of(EntityValidationException ex) {
        this.message = ex.getMessage();
        this.errors = ex.getBindingResult().getAllErrors().stream()
                .map(er -> (org.springframework.validation.FieldError) er)
                .map(er -> new ObjectError(er.getField(), er.getDefaultMessage()))
                .toList();
        this.timestamp = LocalDateTime.now().toString();
        return this;
    }
}
