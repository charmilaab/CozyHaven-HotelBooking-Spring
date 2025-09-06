package com.hexaware.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HotelDto {
    private Long hotelId;

    @NotBlank @Size(min = 2, max = 60)
    private String name;

    @NotBlank @Size(min = 2, max = 100)
    private String location;

    @NotBlank @Size(min = 5, max = 500)
    private String description;

    @NotBlank @Size(min = 3, max = 300)
    private String amenities;
}
