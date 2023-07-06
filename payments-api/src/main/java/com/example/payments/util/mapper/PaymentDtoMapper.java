package com.example.payments.util.mapper;

import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper extends GenericMapper<Payment, PaymentDto> {
    CardDtoMapper CARD_DTO_MAPPER = Mappers.getMapper(CardDtoMapper.class);
    UserDtoMapper USER_DTO_MAPPER = Mappers.getMapper(UserDtoMapper.class);

    default PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .sender(CARD_DTO_MAPPER.toDto(payment.getSender()))
                .receiver(CARD_DTO_MAPPER.toDto(payment.getReceiver()))
                .userSender(USER_DTO_MAPPER.toDto(payment.getSender().getUser()))
                .userReceiver(USER_DTO_MAPPER.toDto(payment.getReceiver().getUser()))
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .date(payment.getDate().toString())
                .build();
    }
}
