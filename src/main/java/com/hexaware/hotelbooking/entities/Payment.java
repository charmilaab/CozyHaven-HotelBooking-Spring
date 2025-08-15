package com.hexaware.hotelbooking.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference(value = "booking-payment")
    private Booking booking;

    private Double amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime paymentDate;
}