package com.example.payments.util.mapper;

import com.example.payments.dto.OutReceiverPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CardDtoMapper.class)
public interface PaymentOutReceiverDtoMapper extends GenericMapper<Payment, OutReceiverPaymentDto>{
}
