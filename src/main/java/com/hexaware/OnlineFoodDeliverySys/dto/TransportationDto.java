package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransportationDto {

    @Min(1)
    private Long transportId;

    @Pattern(regexp = "^(Car|Bus|Van|Bike)$", message = "Transport type must be Car, Bus, Van, or Bike")
    private String type;

    @Size(max = 300, message = "Details cannot exceed 300 characters")
    private String details;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double cost;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;
}
