package com.example.payments.util.mapper;

import com.example.payments.dto.InPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = CardDtoMapper.class)
public interface PaymentInPaymentDtoMapper extends GenericMapper<Payment, InPaymentDto> {
}
