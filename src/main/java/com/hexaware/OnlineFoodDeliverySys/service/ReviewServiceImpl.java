package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.*;
import com.hexaware.OnlineFoodDeliverySys.exceptions.*;
import com.hexaware.OnlineFoodDeliverySys.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired private ReviewRepository repo;
    @Autowired private HotelRepository hotelRepo;
    @Autowired private UserRepository userRepo;

    @Override
    public Review addReview(ReviewDto dto) {
        Hotel h = hotelRepo.findById(dto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found: " + dto.getHotelId()));
        User u = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + dto.getUserId()));

        Review r = new Review();
        r.setReviewId(dto.getReviewId());
        r.setHotel(h);
        r.setUser(u);
        r.setComment(dto.getComment());
        r.setRating(dto.getRating());
        return repo.save(r);
    }

    @Override
    public Review updateReview(Review review) {
        repo.findById(review.getReviewId())
            .orElseThrow(() -> new ReviewNotFoundException("Review not found: " + review.getReviewId()));
        return repo.save(review);
    }

    @Override
    public Review getByReviewId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found: " + id));
    }

    @Override
    public String deleteByReviewId(Long id) {
        Review r = getByReviewId(id);
        repo.delete(r);
        return "Review deleted successfully";
    }

    @Override
    public List<Review> getAllReviews() { return repo.findAll(); }

    @Override
    public List<Review> getReviewsByHotel(Long hotelId) { return repo.findReviewsByHotel(hotelId); }
}
