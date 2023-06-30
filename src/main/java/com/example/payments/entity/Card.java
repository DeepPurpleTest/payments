package com.example.payments.entity;

import com.example.payments.view.identifiable.AbstractCardIdentifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "card")
@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable, AbstractCardIdentifiable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "balance")
    private BigDecimal balance;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status status;
    @JsonBackReference
    @OneToMany(mappedBy = "sender")
    private List<Payment> likeSenderPayments;

    @JsonBackReference
    @OneToMany(mappedBy = "receiver")
    private List<Payment> likeReceiverPayments;

    @JsonBackReference
    @OneToMany(mappedBy = "card")
    private List<Request> requests;
}
