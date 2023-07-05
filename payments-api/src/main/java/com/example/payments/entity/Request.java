package com.example.payments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JoinColumn(name = "card_number", referencedColumnName = "card_number")
    @JsonIgnore
    private Card card;
    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    @JsonIgnore
    private User admin;
}
