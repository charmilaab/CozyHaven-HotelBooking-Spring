package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.ReviewDto;
import com.hexaware.hotelbooking.entities.Review;
import com.hexaware.hotelbooking.service.ReviewService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired private ReviewService service;

    @PostMapping("/insert")
    public Review add(@RequestBody @Valid ReviewDto dto) { return service.addReview(dto); }

    @PutMapping("/update")
    public Review update(@RequestBody @Valid Review review) { return service.updateReview(review); }

    @GetMapping("/getbyid/{reviewId}")
    public Review get(@PathVariable Long reviewId) { return service.getByReviewId(reviewId); }

    @DeleteMapping("/deletebyid/{reviewId}")
    public String delete(@PathVariable Long reviewId) { return service.deleteByReviewId(reviewId); }

    @GetMapping("/getall")
    public List<Review> all() { return service.getAllReviews(); }

    @GetMapping("/byhotel/{hotelId}")
    public List<Review> byHotel(@PathVariable Long hotelId) { return service.getReviewsByHotel(hotelId); }
}
