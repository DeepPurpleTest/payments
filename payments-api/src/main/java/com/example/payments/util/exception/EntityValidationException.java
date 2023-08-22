package com.example.payments.util.exception;

import org.springframework.validation.BindingResult;

public class EntityValidationException extends EntityException {
    private final transient BindingResult bindingResult;
    public EntityValidationException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
