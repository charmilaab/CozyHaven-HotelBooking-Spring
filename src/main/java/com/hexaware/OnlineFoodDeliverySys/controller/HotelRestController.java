package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.service.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hotels")
public class HotelRestController {

    @Autowired
    private HotelService service;

    @PostMapping("/insert")
    public Hotel addHotel(@RequestBody @Valid HotelDto dto) {
        return service.addHotel(dto);
    }

    @PutMapping("/update")
    public Hotel updateHotel(@RequestBody Hotel hotel) {
        return service.updateHotel(hotel);
    }

    @GetMapping("/getbyid/{hotelId}")
    public Hotel getByHotelId(@PathVariable Long hotelId) {
        return service.getByHotelId(hotelId);
    }

    @DeleteMapping("/deletebyid/{hotelId}")
    public String deleteByHotelId(@PathVariable Long hotelId) {
        return service.deleteByHotelId(hotelId);
    }

    @GetMapping("/searchbylocation/{location}")
    public List<Hotel> searchByLocation(@PathVariable String location) {
        return service.searchHotelsByLocation(location);
    }

    @GetMapping("/getall")
    public List<Hotel> getAllHotels() {
        return service.getAllHotels();
    }
}
