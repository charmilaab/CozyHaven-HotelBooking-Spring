package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.ReviewDto;
import com.hexaware.hotelbooking.entities.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(ReviewDto dto);
    Review updateReview(Review review);
    Review getByReviewId(Long id);
    String deleteByReviewId(Long id);
    List<Review> getAllReviews();
    List<Review> getReviewsByHotel(Long hotelId);
}
