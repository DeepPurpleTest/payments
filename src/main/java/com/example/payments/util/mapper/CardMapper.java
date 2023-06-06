package com.example.payments.util.mapper;

import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CardMapper extends GenericMapper<Card, CardDto> {
}
