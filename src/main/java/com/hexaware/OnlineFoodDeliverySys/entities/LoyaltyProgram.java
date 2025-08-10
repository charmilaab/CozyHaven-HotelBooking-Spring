package com.hexaware.OnlineFoodDeliverySys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "loyalty_program")
public class LoyaltyProgram {

    @Id
    private Long loyaltyId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-loyalty")
    private User user;

    private Integer points;
    private String tier;
}
