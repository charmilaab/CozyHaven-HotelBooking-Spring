package com.hexaware.OnlineFoodDeliverySys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    private Long roomId;

    private String roomType;
    private Double baseFare;
    private Integer maxOccupancy;
    private String bedType;
    private String size;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference(value = "hotel-room")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "room-booking")
    private List<Booking> bookings;
}
