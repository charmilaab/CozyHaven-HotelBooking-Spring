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
        log.info("Adding new hotel: {}", dto);
        return service.addHotel(dto);
    }

    
    @PutMapping("/update/{id}")
    public Hotel updateHotel(@PathVariable Long id, @RequestBody @Valid Hotel hotel) {
        hotel.setHotelId(id);
        return service.updateHotel(hotel);
    }

    
    @DeleteMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        return service.deleteHotel(id);
    }

    
    @GetMapping("/getall")
    public List<Hotel> getAllHotels() {
        return service.getAllHotels();
    }

    @GetMapping("/getbyid/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return service.getByHotelId(id);
    }
}
