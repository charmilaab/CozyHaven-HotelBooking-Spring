package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {
    @NotNull @Positive
    private Long bookingId;

    @NotNull @Positive
    private Long userId;

    @NotNull @Positive
    private Long roomId;

    @NotNull @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull @Min(1) @Max(20)
    private Integer numberOfRooms;

    @NotNull @Positive
    private Double totalAmount;

    @NotBlank
    @Pattern(regexp = "^(PENDING|CONFIRMED|CANCELLED)$", message = "status must be PENDING/CONFIRMED/CANCELLED")
    private String status;
}
