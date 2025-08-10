package com.hexaware.OnlineFoodDeliverySys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    @Query("SELECT t FROM Transportation t WHERE t.hotel.hotelId = :hotelId")
    List<Transportation> findTransportOptionsByHotel(Long hotelId);
}
