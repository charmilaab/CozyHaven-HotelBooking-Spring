package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;
import com.hexaware.OnlineFoodDeliverySys.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService service;

    @PostMapping("/insert")
    public Review addReview(@RequestBody @Valid ReviewDto dto) {
        return service.addReview(dto);
    }

    @PutMapping("/update")
    public Review updateReview(@RequestBody Review review) {
        return service.updateReview(review);
    }

    @GetMapping("/getbyid/{reviewId}")
    public Review getByReviewId(@PathVariable Long reviewId) {
        return service.getByReviewId(reviewId);
    }

    @DeleteMapping("/deletebyid/{reviewId}")
    public String deleteByReviewId(@PathVariable Long reviewId) {
        return service.deleteByReviewId(reviewId);
    }

    @GetMapping("/getbyhotel/{hotelId}")
    public List<Review> getReviewsByHotel(@PathVariable Long hotelId) {
        return service.getReviewsByHotel(hotelId);
    }

    @GetMapping("/getall")
    public List<Review> getAllReviews() {
        return service.getAllReviews();
    }
}
