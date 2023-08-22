package com.example.payments.util.response;

public interface ResponseWrapper<T, E extends RuntimeException> {
    T of(E ex);
}
