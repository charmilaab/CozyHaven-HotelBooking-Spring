package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDto {

    @Min(1)
    private Long roomId;

    @Pattern(regexp = "^(Single|Double|King)$", message = "Room type must be Single, Double, or King")
    private String roomType;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double baseFare;

    @Min(1)
    @Max(10)
    private Integer maxOccupancy;

    @Pattern(regexp = "^(Single Bed|Double Bed|King Bed)$", message = "Bed type must be Single Bed, Double Bed, or King Bed")
    private String bedType;

    @Pattern(regexp = "^[0-9]{1,3} m²$", message = "Size must be in format '70 m²'")
    private String size;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;
}
