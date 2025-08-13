package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.exceptions.HotelNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.exceptions.RoomNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired private RoomRepository repo;
    @Autowired private HotelRepository hotelRepo;

    @Override
    public Room addRoom(RoomDto dto) {
        Hotel hotel = hotelRepo.findById(dto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found: " + dto.getHotelId()));
        Room r = new Room();
        r.setRoomId(dto.getRoomId());
        r.setHotel(hotel);
        r.setRoomType(dto.getRoomType());
        r.setBaseFare(dto.getBaseFare());
        r.setMaxOccupancy(dto.getMaxOccupancy());
        r.setBedType(dto.getBedType());
        r.setSize(dto.getSize());
        return repo.save(r);
    }

    @Override
    public Room updateRoom(Room room) {
        repo.findById(room.getRoomId())
            .orElseThrow(() -> new RoomNotFoundException("Room not found: " + room.getRoomId()));
        return repo.save(room);
    }

    @Override
    public Room getByRoomId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
    }

    @Override
    public String deleteByRoomId(Long id) {
        Room r = getByRoomId(id);
        repo.delete(r);
        return "Room deleted successfully";
    }

    @Override
    public List<Room> getAllRooms() { return repo.findAll(); }

    @Override
    public List<Room> getByHotel(Long hotelId) {
        return repo.findRoomsByHotelId(hotelId);
    }
}
