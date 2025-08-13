package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoomDto {
    @NotNull @Positive
    private Long roomId;

    @NotNull @Positive
    private Long hotelId;

    @NotBlank
    @Pattern(regexp = "^(Single|Double|King|Suite)$", message = "roomType must be Single/Double/King/Suite")
    private String roomType;

    @NotNull @Positive
    private Double baseFare;

    @NotNull @Min(1) @Max(10)
    private Integer maxOccupancy;

    @NotBlank
    @Pattern(regexp = "^(single|double|king)$", message = "bedType must be single/double/king")
    private String bedType;

    @NotBlank @Size(min = 2, max = 30) // e.g., "70 m²"
    private String size;
}
