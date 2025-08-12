package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDto {

    @Min(1)
    private Long bookingId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @FutureOrPresent(message = "Check-in date must be today or later")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(1)
    @Max(10)
    private Integer numberOfRooms;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double totalAmount;

    @Pattern(regexp = "^(CONFIRMED|CANCELLED|PENDING)$", message = "Status must be CONFIRMED, CANCELLED, or PENDING")
    private String status;
}
