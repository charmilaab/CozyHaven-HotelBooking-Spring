package com.hexaware.OnlineFoodDeliverySys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
