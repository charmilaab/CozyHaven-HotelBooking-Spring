package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;

public interface ReviewService {
    Review addReview(ReviewDto dto);
    Review updateReview(Review review);
    Review getByReviewId(Long reviewId);
    String deleteByReviewId(Long reviewId);
    List<Review> getAllReviews();
    List<Review> getByHotelId(Long hotelId);
}
