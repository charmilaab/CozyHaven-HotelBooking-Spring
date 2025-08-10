package com.hexaware.OnlineFoodDeliverySys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.OnlineFoodDeliverySys.entities.LoyaltyProgram;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {

    @Query("SELECT l FROM LoyaltyProgram l WHERE l.user.userId = :userId")
    Optional<LoyaltyProgram> findByUserId(Long userId);
}
