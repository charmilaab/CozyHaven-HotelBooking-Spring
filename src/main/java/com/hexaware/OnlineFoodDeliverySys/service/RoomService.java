package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room);
    Room getRoomById(Long roomId);
    List<Room> getAllRooms();
    Room updateRoom(Long roomId, Room room);
    void deleteRoom(Long roomId);
}
