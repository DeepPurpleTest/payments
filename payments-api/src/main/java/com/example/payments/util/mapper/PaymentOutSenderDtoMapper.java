package com.example.payments.util.mapper;

import com.example.payments.dto.OutSenderPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = CardDtoMapper.class)
public interface PaymentOutSenderDtoMapper extends GenericMapper<Payment, OutSenderPaymentDto> {

}
