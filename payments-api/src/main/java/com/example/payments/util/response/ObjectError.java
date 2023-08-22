package com.example.payments.util.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectError {
    private String fieldName;
    private String fieldError;
}
