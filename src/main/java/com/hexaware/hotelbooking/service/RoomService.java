package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.RoomDto;
import com.hexaware.hotelbooking.entities.Room;

import java.util.List;

public interface RoomService {
    Room addRoom(RoomDto dto);
    Room updateRoom(RoomDto dto);
    Room getRoomById(Long roomId);
    List<Room> getRoomsByHotelId(Long hotelId);
    void deleteRoom(Long roomId);
}
