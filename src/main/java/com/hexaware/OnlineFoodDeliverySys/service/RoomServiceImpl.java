package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository repo;

    @Autowired
    private HotelRepository hotelRepo;

    @Override
    public Room addRoom(RoomDto dto) {
        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        room.setRoomType(dto.getRoomType());
        room.setBaseFare(dto.getBaseFare());
        room.setMaxOccupancy(dto.getMaxOccupancy());
        room.setBedType(dto.getBedType());
        room.setSize(dto.getSize());
        room.setHotel(hotelRepo.findById(dto.getHotelId()).orElse(null));
        return repo.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        return repo.save(room);
    }

    @Override
    public Room getByRoomId(Long roomId) {
        return repo.findById(roomId).orElse(null);
    }

    @Override
    public String deleteByRoomId(Long roomId) {
        repo.deleteById(roomId);
        return "Room deleted successfully";
    }

    @Override
    public List<Room> getAllRooms() {
        return repo.findAll();
    }

    @Override
    public List<Room> getRoomsByHotel(Long hotelId) {
        return repo.findRoomsByHotelId(hotelId);
    }
}
