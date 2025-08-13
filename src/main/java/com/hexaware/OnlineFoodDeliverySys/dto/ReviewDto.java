package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewDto {
    @NotNull @Positive
    private Long reviewId;

    @NotNull @Positive
    private Long hotelId;

    @NotNull @Positive
    private Long userId;

    @NotBlank @Size(min = 2, max = 300)
    private String comment;

    @Min(1) @Max(5)
    private int rating;
}
