package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.ReviewNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.ReviewRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Override
    public Review addReview(ReviewDto dto) {
        Review review = new Review();
        review.setReviewId(dto.getReviewId());

        // Map UserDto -> User entity (if provided)
        if (dto.getUser() != null) {
            User user = new User();
            if (dto.getUser().getUserId() != null) {
                user.setUserId(dto.getUser().getUserId());
            }
            // you can map more fields here if needed
            review.setUser(user);
        }

        // Map HotelDto -> Hotel entity (if provided)
        if (dto.getHotel() != null) {
            Hotel hotel = new Hotel();
            if (dto.getHotel().getHotelId() != null) {
                hotel.setHotelId(dto.getHotel().getHotelId());
            }
            // you can map more fields here if needed
            review.setHotel(hotel);
        }

        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        log.info("Adding new review: {}", dto);
        return reviewRepo.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        if (!reviewRepo.existsById(review.getReviewId())) {
            throw new ReviewNotFoundException("Review not found with ID: " + review.getReviewId());
        }
        return reviewRepo.save(review);
    }

    @Override
    public Review getByReviewId(Long reviewId) {
        return reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));
    }

    @Override
    public String deleteByReviewId(Long reviewId) {
        if (!reviewRepo.existsById(reviewId)) {
            throw new ReviewNotFoundException("Cannot delete. Review not found with ID: " + reviewId);
        }
        reviewRepo.deleteById(reviewId);
        return "Review deleted successfully";
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    @Override
    public List<Review> getByHotelId(Long hotelId) {
        return reviewRepo.findReviewsByHotel(hotelId);
    }
}
