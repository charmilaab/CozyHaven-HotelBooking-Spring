package com.hexaware.OnlineFoodDeliverySys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference(value = "hotel-review")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-review")
    private User user;

    private String comment;
    private int rating;
}
