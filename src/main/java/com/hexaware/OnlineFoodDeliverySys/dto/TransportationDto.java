package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransportationDto {

    private Long transportId;

    @NotBlank(message = "Type is required")
    private String type;

    private String details;

    @Min(value = 0, message = "Cost must be positive")
    private Double cost;

    private HotelDto hotel;
}
