package com.hexaware.hotelbooking.dto;

import com.hexaware.hotelbooking.entities.Transportation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long bookingId;
    private Long transportId;
    private Transportation transport;
    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Room ID must not be null")
    private Long roomId;

    @NotNull(message = "Check-in date must not be null")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date must not be null")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Positive(message = "Number of rooms must be greater than zero")
    private Integer numberOfRooms; // Optional

    @Positive(message = "Total amount must be positive")
    private Double totalAmount; // Optional

    @NotNull(message = "Status must not be null")
    @Size(min = 3, max = 20, message = "Status must be between 3 and 20 characters")
    private String status;
}
