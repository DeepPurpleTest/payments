package com.example.payments.util.mapper;

import com.example.payments.dto.InPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = CardMapper.class)
public interface InPaymentMapper extends GenericMapper<Payment, InPaymentDto> {
}
