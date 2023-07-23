package com.example.payments.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Builder
@Jacksonized
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "card_number")
    private Card sender;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "card_number")
    private Card receiver;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "card_sender_balance")
    private BigDecimal senderBalanceAfterPayment;
    @Column(name = "card_receiver_balance")
    private BigDecimal receiverBalanceAfterPayment;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "date")
    private LocalDateTime date;
}
