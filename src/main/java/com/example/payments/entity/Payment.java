package com.example.payments.entity;

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

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "card_number")
    private Card sender;
    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "card_number")
    private Card receiver;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "date")
    private LocalDateTime date;
}
