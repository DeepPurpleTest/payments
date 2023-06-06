package com.example.payments.util.mapper;

public interface GenericMapper<T, U> {
    U toDto(T entity);
    T toEntity(U dto);
}
