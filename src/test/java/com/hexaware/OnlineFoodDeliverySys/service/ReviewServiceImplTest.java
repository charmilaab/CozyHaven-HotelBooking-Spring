package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.ReviewDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;
import com.hexaware.OnlineFoodDeliverySys.exceptions.ReviewNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository repo;

    @InjectMocks
    private ReviewServiceImpl service;

    private Review review;
    private ReviewDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new Review();
        review.setReviewId(501L);
        review.setComment("Excellent service and clean rooms");
        review.setRating(5);

        dto = new ReviewDto();
        dto.setReviewId(501L);
        dto.setComment("Excellent service and clean rooms");
        dto.setRating(5);
    }

    @Test
    void testAddReview() {
        when(repo.save(any(Review.class))).thenReturn(review);
        Review saved = service.addReview(dto);
        assertEquals(5, saved.getRating());
    }

    @Test
    void testGetByReviewId_Found() {
        when(repo.findById(501L)).thenReturn(Optional.of(review));
        assertEquals("Excellent service and clean rooms", service.getByReviewId(501L).getComment());
    }

    @Test
    void testGetByReviewId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ReviewNotFoundException.class, () -> service.getByReviewId(999L));
    }

    @Test
    void testGetAllReviews() {
        when(repo.findAll()).thenReturn(Arrays.asList(review));
        assertEquals(1, service.getAllReviews().size());
    }

    @Test
    void testDeleteByReviewId() {
        when(repo.findById(501L)).thenReturn(Optional.of(review));
        String result = service.deleteByReviewId(501L);
        assertEquals("Review deleted successfully", result);
    }
}
