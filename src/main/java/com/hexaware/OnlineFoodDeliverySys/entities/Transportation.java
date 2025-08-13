package com.hexaware.OnlineFoodDeliverySys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transportation")
public class Transportation {

    @Id
    private Long transportId;

    private String type;
    private String details;
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference(value = "hotel-transport")
    private Hotel hotel;
}