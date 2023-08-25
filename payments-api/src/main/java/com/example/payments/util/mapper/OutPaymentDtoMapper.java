package com.example.payments.util.mapper;

import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import com.example.payments.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CardDtoMapper.class, CustomDateMapper.class})
public interface OutPaymentDtoMapper extends GenericMapper<Payment, OutPaymentDto> {
    @Mapping(source = "payment.id", target = "id")
    @Mapping(source = "payment.status", target = "status")
    @Mapping(source = "payment.date", target = "date")
    OutPaymentDto toDto(Payment payment, User user);

    @AfterMapping
    default void updateDto(@MappingTarget OutPaymentDto.OutPaymentDtoBuilder dto, Payment payment, User user) {
        Card sender = payment.getSender();
        Card receiver = payment.getReceiver();
        if(sender.getUser().getId().equals(user.getId())) {
            dto.currentUserCard(sender.getCardNumber());
            dto.currentCardBalance(payment.getSenderBalanceAfterPayment());
        } else if(receiver.getUser().getId().equals(user.getId())){
            dto.currentUserCard(receiver.getCardNumber());
            dto.currentCardBalance(payment.getReceiverBalanceAfterPayment());
        }
    }


}
