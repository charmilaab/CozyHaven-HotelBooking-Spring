package com.hexaware.OnlineFoodDeliverySys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.OnlineFoodDeliverySys.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
