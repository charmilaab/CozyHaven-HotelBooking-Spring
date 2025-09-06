package com.hexaware.hotelbooking.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.hexaware.hotelbooking.entities.Transportation;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    @Query("SELECT t FROM Transportation t WHERE t.hotel.hotelId = :hotelId")
    List<Transportation> findTransportOptionsByHotel(Long hotelId);
}
