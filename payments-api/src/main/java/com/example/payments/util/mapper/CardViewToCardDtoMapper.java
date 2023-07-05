package com.example.payments.util.mapper;

import com.example.payments.dto.CardDto;
import com.example.payments.view.CardView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CardDtoMapper.class)
public interface CardViewToCardDtoMapper extends ViewToDtoMapper<CardView, CardDto> {
    default String cardToString(CardView view) {
        return view.getCardNumber();
    }
}
