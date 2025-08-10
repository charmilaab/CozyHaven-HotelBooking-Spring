package com.hexaware.OnlineFoodDeliverySys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
