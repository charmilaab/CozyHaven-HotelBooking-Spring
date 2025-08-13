package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/rooms")
public class RoomRestController {
    @Autowired private RoomService service;

    @PostMapping("/insert")
    public Room add(@RequestBody @Valid RoomDto dto) { return service.addRoom(dto); }

    @PutMapping("/update")
    public Room update(@RequestBody @Valid Room room) { return service.updateRoom(room); }

    @GetMapping("/getbyid/{roomId}")
    public Room get(@PathVariable Long roomId) { return service.getByRoomId(roomId); }

    @DeleteMapping("/deletebyid/{roomId}")
    public String delete(@PathVariable Long roomId) { return service.deleteByRoomId(roomId); }

    @GetMapping("/getall")
    public List<Room> all() { return service.getAllRooms(); }

    @GetMapping("/byhotel/{hotelId}")
    public List<Room> byHotel(@PathVariable Long hotelId) { return service.getByHotel(hotelId); }
}
