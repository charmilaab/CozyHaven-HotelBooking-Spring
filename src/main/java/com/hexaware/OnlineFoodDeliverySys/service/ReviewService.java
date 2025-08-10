package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.entities.Review;
import java.util.List;

public interface ReviewService {
    Review createReview(Review review);
    Review getReviewById(Long reviewId);
    List<Review> getAllReviews();
    Review updateReview(Long reviewId, Review review);
    void deleteReview(Long reviewId);
}
