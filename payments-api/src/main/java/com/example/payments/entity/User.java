package com.example.payments.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@Jacksonized
@Table(name = "user_account")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "role_id")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status status;
    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Card> cards;
    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Request> sentRequests;

    @JsonBackReference
    @OneToMany(mappedBy = "admin")
    private List<Request> processedRequests;
}
