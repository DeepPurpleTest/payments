package com.example.payments.util.mapper;

import com.example.payments.dto.OutSenderPaymentDto;
import com.example.payments.view.OutSenderReceiverPaymentView;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = CardViewToCardDtoMapper.class)
public interface OutSenderReceiverViewToOutSenderPaymentDtoMapper extends ViewToDtoMapper<OutSenderReceiverPaymentView, OutSenderPaymentDto> {
}
