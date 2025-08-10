package com.hexaware.OnlineFoodDeliverySys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.OnlineFoodDeliverySys.entities.LoyaltyProgram;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
}
