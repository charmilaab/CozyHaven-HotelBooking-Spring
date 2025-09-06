package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.RoomDto;
import com.hexaware.hotelbooking.entities.Room;
import com.hexaware.hotelbooking.service.RoomService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomRestController {

    private final RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<?> addRoom(@Valid @RequestBody RoomDto dto) {
        Room room = roomService.addRoom(dto);
        return ResponseEntity.ok("Room added successfully with ID: " + room.getRoomId());
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomId, @Valid @RequestBody RoomDto dto) {
        dto.setRoomId(roomId);
        Room room = roomService.updateRoom(dto);
        return ResponseEntity.ok("Room updated successfully with ID: " + room.getRoomId());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<?> getRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getRoomsByHotelId(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted successfully with ID: " + roomId);
    }
}