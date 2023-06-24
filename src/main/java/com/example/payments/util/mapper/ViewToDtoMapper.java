package com.example.payments.util.mapper;

public interface ViewToDtoMapper<T, U> {
    U toDto(T view);
}
