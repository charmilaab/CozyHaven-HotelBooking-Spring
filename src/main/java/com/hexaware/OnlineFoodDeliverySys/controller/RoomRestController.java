package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.service.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {

    @Autowired
    private RoomService service;

    @PostMapping("/insert")
    public Room addRoom(@RequestBody @Valid RoomDto dto) {
        log.info("Received request to add room: {}", dto);
        return service.addRoom(dto);
    }

    @PutMapping("/update")
    public Room updateRoom(@RequestBody @Valid Room room) {
        log.info("Received request to update room: {}", room);
        return service.updateRoom(room);
    }

    @GetMapping("/getbyid/{roomId}")
    public Room getByRoomId(@PathVariable Long roomId) {
        log.info("Received request to fetch room with ID: {}", roomId);
        return service.getByRoomId(roomId);
    }

    @DeleteMapping("/deletebyid/{roomId}")
    public String deleteByRoomId(@PathVariable Long roomId) {
        log.info("Received request to delete room with ID: {}", roomId);
        return service.deleteByRoomId(roomId);
    }

    @GetMapping("/getall")
    public List<Room> getAllRooms() {
        log.info("Received request to fetch all rooms");
        return service.getAllRooms();
    }
}
