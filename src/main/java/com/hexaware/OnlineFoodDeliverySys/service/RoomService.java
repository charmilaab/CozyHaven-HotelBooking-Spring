package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;

public interface RoomService {
    Room addRoom(RoomDto dto);
    Room updateRoom(Room room);
    Room getByRoomId(Long roomId);
    String deleteByRoomId(Long roomId);
    List<Room> getAllRooms();
    List<Room> getByHotelId(Long hotelId);
}
