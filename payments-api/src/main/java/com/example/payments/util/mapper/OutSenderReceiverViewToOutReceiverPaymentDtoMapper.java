package com.example.payments.util.mapper;

import com.example.payments.dto.OutReceiverPaymentDto;
import com.example.payments.view.OutSenderReceiverPaymentView;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = CardViewToCardDtoMapper.class)
public interface OutSenderReceiverViewToOutReceiverPaymentDtoMapper extends ViewToDtoMapper<OutSenderReceiverPaymentView, OutReceiverPaymentDto>{
}
