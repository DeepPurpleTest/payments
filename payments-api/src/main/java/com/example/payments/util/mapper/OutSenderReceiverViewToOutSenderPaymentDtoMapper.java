package com.example.payments.util.mapper;

import com.example.payments.dto.OutSenderPaymentDto;
import com.example.payments.view.OutSenderReceiverPaymentView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring", uses = CardViewToCardDtoMapper.class)
public interface OutSenderReceiverViewToOutSenderPaymentDtoMapper extends ViewToDtoMapper<OutSenderReceiverPaymentView, OutSenderPaymentDto> {
    @Override
    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy HH:mm")
    OutSenderPaymentDto toDto(OutSenderReceiverPaymentView view);
}
