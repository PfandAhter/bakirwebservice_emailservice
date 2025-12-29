package com.bakirwebservice.emailservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Table (name = "email_validator")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmailValidator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "email_validator_id")
    private String emailValidatorId;

    @Column(name = "username")
    private String username;

}
