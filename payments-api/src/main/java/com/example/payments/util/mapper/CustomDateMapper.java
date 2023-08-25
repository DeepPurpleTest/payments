package com.example.payments.util.mapper;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface CustomDateMapper {
    default Long localDateTimeToLong(LocalDateTime date) {
        if(date == null) {
            return null;
        }
        return date.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    default LocalDateTime longToLocalDateTime(Long timestamp) {
        if(timestamp == null) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(timestamp);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
