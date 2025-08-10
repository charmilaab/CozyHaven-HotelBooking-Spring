package com.hexaware.OnlineFoodDeliverySys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.OnlineFoodDeliverySys.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.hotel.hotelId = :hotelId ORDER BY r.rating DESC")
    List<Review> findReviewsByHotel(Long hotelId);
}
