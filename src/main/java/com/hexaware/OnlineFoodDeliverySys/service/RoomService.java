package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;

import java.util.List;

public interface RoomService {
    Room addRoom(RoomDto dto);
    Room updateRoom(RoomDto dto);
    Room getRoomById(Long roomId);
    List<Room> getRoomsByHotelId(Long hotelId);
    void deleteRoom(Long roomId);
}
