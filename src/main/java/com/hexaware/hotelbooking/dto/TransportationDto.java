package com.hexaware.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TransportationDto {
    private Long transportId;

    @NotNull @Positive
    private Long hotelId;

    @NotBlank
    @Pattern(regexp = "^(Airport Pickup|Cab|Bus)$", message = "type must be Airport Pickup/Cab/Bus")
    private String type;

    @NotBlank @Size(min = 3, max = 200)
    private String details;

    @NotNull @Positive
    private Double cost;
}
