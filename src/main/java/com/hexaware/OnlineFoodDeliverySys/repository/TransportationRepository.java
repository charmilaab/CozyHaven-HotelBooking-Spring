package com.hexaware.OnlineFoodDeliverySys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
}
