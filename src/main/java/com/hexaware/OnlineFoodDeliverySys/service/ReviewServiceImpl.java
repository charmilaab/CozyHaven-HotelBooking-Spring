package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.ReviewRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository repo;

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Review addReview(ReviewDto dto) {
        Review review = new Review();
        review.setReviewId(dto.getReviewId());
        Hotel hotel = hotelRepo.findById(dto.getHotelId()).orElse(null);
        User user = userRepo.findById(dto.getUserId()).orElse(null);
        review.setHotel(hotel);
        review.setUser(user);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        return repo.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        return repo.save(review);
    }

    @Override
    public Review getByReviewId(Long reviewId) {
        return repo.findById(reviewId).orElse(null);
    }

    @Override
    public String deleteByReviewId(Long reviewId) {
        repo.deleteById(reviewId);
        return "Review deleted successfully";
    }

    @Override
    public List<Review> getAllReviews() {
        return repo.findAll();
    }

    @Override
    public List<Review> getReviewsByHotel(Long hotelId) {
        return repo.findReviewsByHotel(hotelId);
    }
}
