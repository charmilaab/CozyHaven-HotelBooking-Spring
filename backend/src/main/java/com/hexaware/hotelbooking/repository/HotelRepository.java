package com.hexaware.hotelbooking.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.hexaware.hotelbooking.entities.Hotel;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Hotel> searchHotelsByLocation(String location);
}
