package com.hexaware.OnlineFoodDeliverySys.repository;

import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Hotel> searchHotelsByLocation(String location);
}
