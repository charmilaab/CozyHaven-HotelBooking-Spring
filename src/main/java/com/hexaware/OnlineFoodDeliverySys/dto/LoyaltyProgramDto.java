package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoyaltyProgramDto {

    private Long loyaltyId;

    private UserDto user;

    @Min(value = 0, message = "Points cannot be negative")
    private Integer points;

    @NotBlank(message = "Tier is required")
    private String tier;
}
