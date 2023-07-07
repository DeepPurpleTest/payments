package com.example.payments.util.mapper;

import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper extends GenericMapper<Payment, PaymentDto> {
    CardDtoMapper CARD_DTO_MAPPER = Mappers.getMapper(CardDtoMapper.class);
    UserDtoMapper USER_DTO_MAPPER = Mappers.getMapper(UserDtoMapper.class);

    default PaymentDto toDto(Payment payment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return PaymentDto.builder()
                .sender(CARD_DTO_MAPPER.toDto(payment.getSender()))
                .receiver(CARD_DTO_MAPPER.toDto(payment.getReceiver()))
                .userSender(USER_DTO_MAPPER.toDto(payment.getSender().getUser()))
                .userReceiver(USER_DTO_MAPPER.toDto(payment.getReceiver().getUser()))
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .date(payment.getDate().format(formatter))
                .build();
    }
}
