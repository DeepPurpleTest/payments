package com.example.payments.util.mapper;

import com.example.payments.dto.OutReceiverPaymentDto;
import com.example.payments.view.OutSenderReceiverPaymentView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring", uses = CardViewToCardDtoMapper.class)
public interface OutSenderReceiverViewToOutReceiverPaymentDtoMapper extends ViewToDtoMapper<OutSenderReceiverPaymentView, OutReceiverPaymentDto>{
    @Override
    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy HH:mm")
    OutReceiverPaymentDto toDto(OutSenderReceiverPaymentView view);
}
