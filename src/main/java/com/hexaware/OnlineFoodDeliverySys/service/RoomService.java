package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import java.util.List;

public interface RoomService {
    Room addRoom(RoomDto dto);
    Room updateRoom(Room room);
    Room getByRoomId(Long id);
    String deleteByRoomId(Long id);
    List<Room> getAllRooms();
    List<Room> getByHotel(Long hotelId);
}
