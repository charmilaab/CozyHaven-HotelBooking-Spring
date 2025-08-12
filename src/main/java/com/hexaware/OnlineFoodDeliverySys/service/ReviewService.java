package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(ReviewDto dto);
    Review updateReview(Review review);
    Review getByReviewId(Long reviewId);
    String deleteByReviewId(Long reviewId);
    List<Review> getAllReviews();
    List<Review> getReviewsByHotel(Long hotelId);
}
