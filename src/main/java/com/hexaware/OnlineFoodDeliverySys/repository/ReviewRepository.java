package com.hexaware.OnlineFoodDeliverySys.repository;

import com.hexaware.OnlineFoodDeliverySys.entities.Review;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.hotel.hotelId = :hotelId ORDER BY r.rating DESC")
    List<Review> findReviewsByHotel(Long hotelId);
}
