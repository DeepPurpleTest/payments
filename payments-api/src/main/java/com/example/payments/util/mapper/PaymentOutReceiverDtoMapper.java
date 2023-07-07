package com.example.payments.util.mapper;

import com.example.payments.dto.OutReceiverPaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CardDtoMapper.class)
public interface PaymentOutReceiverDtoMapper extends GenericMapper<Payment, OutReceiverPaymentDto>{
    @Override
    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy HH:mm")
    OutReceiverPaymentDto toDto(Payment entity);
}
