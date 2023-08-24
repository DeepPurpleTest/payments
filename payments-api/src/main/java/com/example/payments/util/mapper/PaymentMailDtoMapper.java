package com.example.payments.util.mapper;

import com.example.payments.dto.PaymentMailDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class, CardDtoMapper.class})
public interface PaymentMailDtoMapper extends GenericMapper<Payment, PaymentMailDto> {

    @Mapping(source = "payment.sender", target = "sender")
    @Mapping(source = "payment.receiver", target = "receiver")
    @Mapping(source = "payment.sender.user", target = "userSender")
    @Mapping(source = "payment.receiver.user", target = "userReceiver")
//    @Mapping(source = "payment.date", target = "date", dateFormat = "dd-MM-yyyy HH:mm")
    PaymentMailDto toDto(Payment payment);
}
