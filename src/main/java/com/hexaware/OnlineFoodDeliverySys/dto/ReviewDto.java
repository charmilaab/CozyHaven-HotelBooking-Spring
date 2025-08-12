package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {

    @Min(1)
    private Long reviewId;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 300, message = "Comment cannot exceed 300 characters")
    private String comment;

    @Min(1)
    @Max(5)
    private int rating;
}
