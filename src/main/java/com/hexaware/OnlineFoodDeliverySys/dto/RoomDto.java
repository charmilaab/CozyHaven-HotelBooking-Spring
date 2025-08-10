package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDto {

    private Long roomId;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @Min(value = 0, message = "Base fare must be positive")
    private Double baseFare;

    @Min(value = 1, message = "Max occupancy must be at least 1")
    private Integer maxOccupancy;

    private String bedType;

    private String size;

    private HotelDto hotel;
}
