package com.hexaware.hotelbooking.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewDto {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;


    @NotNull @Positive
    private Long hotelId;

    @NotNull @Positive
    private Long userId;

    @NotBlank @Size(min = 2, max = 300)
    private String comment;

    @Min(1) @Max(5)
    private Integer rating;  

}
