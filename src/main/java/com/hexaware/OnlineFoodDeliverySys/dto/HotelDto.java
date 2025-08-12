package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelDto {

    @Min(1)
    private Long hotelId;

    @Pattern(regexp = "^[A-Z][a-zA-Z ]{2,50}$", message = "Hotel name must start with a capital letter")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Amenities cannot be blank")
    private String amenities;
}
