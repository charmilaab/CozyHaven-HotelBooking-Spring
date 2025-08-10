package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDto {

    private Long bookingId;

    @NotNull(message = "User is required")
    private UserDto user;

    @NotNull(message = "Room is required")
    private RoomDto room;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer numberOfRooms;

    private Double totalAmount;

    private String status;
}
