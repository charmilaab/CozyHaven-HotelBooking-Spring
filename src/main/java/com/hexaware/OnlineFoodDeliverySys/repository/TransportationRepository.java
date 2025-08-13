package com.hexaware.OnlineFoodDeliverySys.repository;

import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    @Query("SELECT t FROM Transportation t WHERE t.hotel.hotelId = :hotelId")
    List<Transportation> findTransportOptionsByHotel(Long hotelId);
}
