package com.example.payments.util.mapper;

import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = CardMapper.class)
public interface OutPaymentMapper extends GenericMapper<Payment, OutPaymentDto> {

}
