package com.example.payments.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Entity
@Table(name = "request")
@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "card_number")
    private Card card;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin;
}
