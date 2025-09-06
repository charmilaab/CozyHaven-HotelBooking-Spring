package com.hexaware.hotelbooking.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.hexaware.hotelbooking.entities.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.hotelId = :hotelId")
    List<Room> findRoomsByHotelId(Long hotelId);
}
