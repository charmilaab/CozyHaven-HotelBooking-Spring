package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.RoomDto;
import com.hexaware.hotelbooking.entities.Hotel;
import com.hexaware.hotelbooking.entities.Room;
import com.hexaware.hotelbooking.repository.HotelRepository;
import com.hexaware.hotelbooking.repository.RoomRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Room addRoom(RoomDto dto) {
        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        room.setRoomType(dto.getBedType());
        room.setBaseFare(dto.getBaseFare());
        room.setMaxOccupancy(dto.getMaxOccupancy());
        room.setBedType(dto.getBedType().toLowerCase()); // normalize to lowercase
        room.setSize(dto.getSize());

        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        room.setHotel(hotel);

        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(RoomDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(dto.getBedType());
        room.setBaseFare(dto.getBaseFare());
        room.setMaxOccupancy(dto.getMaxOccupancy());
        room.setBedType(dto.getBedType().toLowerCase());
        room.setSize(dto.getSize());

        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        room.setHotel(hotel);

        return roomRepository.save(room);
    }

    @Override
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findRoomsByHotelId(hotelId);
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        roomRepository.delete(room);
    }
}
