package com.example.payments.util.mapper;

import com.example.payments.dto.OutSenderPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring", uses = CardDtoMapper.class)
public interface PaymentOutSenderDtoMapper extends GenericMapper<Payment, OutSenderPaymentDto> {
    @Override
    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy HH:mm")
    OutSenderPaymentDto toDto(Payment entity);
}
