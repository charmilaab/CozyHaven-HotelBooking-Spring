package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.exceptions.RoomNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.RoomRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public Room addRoom(RoomDto dto) {
        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        room.setRoomType(dto.getRoomType());
        room.setBaseFare(dto.getBaseFare());
        room.setMaxOccupancy(dto.getMaxOccupancy());
        room.setBedType(dto.getBedType());
        room.setSize(dto.getSize());

        // Convert HotelDto → Hotel entity
        if (dto.getHotel() != null) {
            Hotel hotel = new Hotel();
            hotel.setHotelId(dto.getHotel().getHotelId());
            // You can set more fields from dto.getHotel() if needed
            room.setHotel(hotel);
        }

        log.info("Adding new room: {}", dto);
        return roomRepo.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        if (!roomRepo.existsById(room.getRoomId())) {
            throw new RoomNotFoundException("Room not found with ID: " + room.getRoomId());
        }
        return roomRepo.save(room);
    }

    @Override
    public Room getByRoomId(Long roomId) {
        return roomRepo.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));
    }

    @Override
    public String deleteByRoomId(Long roomId) {
        if (!roomRepo.existsById(roomId)) {
            throw new RoomNotFoundException("Cannot delete. Room not found with ID: " + roomId);
        }
        roomRepo.deleteById(roomId);
        return "Room deleted successfully";
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public List<Room> getByHotelId(Long hotelId) {
        return roomRepo.findRoomsByHotelId(hotelId);
    }
}
