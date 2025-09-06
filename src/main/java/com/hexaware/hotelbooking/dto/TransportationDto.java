package com.hexaware.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TransportationDto {
    private Long transportId;

    private Long hotelId;

    @NotBlank
    @Pattern(regexp = "^(Cab|Bus|Bike)$", message = "type must be Cab/Bus/Bike")
    private String type;

    @NotBlank @Size(min = 3, max = 200)
    private String details;

    @NotNull @Positive
    private Double cost;
}
