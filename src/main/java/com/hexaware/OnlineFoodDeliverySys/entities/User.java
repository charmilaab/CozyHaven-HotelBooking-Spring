package com.hexaware.OnlineFoodDeliverySys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long userId;

    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String userRole;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-booking")
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-review")
    private List<Review> reviewList;
}