package com.hexaware.OnlineFoodDeliverySys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-booking")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference(value = "room-booking")
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfRooms;
    private Double totalAmount;
    private String status;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "booking-payment")
    private Payment payment;
}
