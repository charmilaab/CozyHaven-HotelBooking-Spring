package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/hotels")
public class HotelRestController {

    @Autowired private HotelService service;

    @PostMapping("/insert")
    public Hotel addHotel(@RequestBody @Valid HotelDto dto) { return service.addHotel(dto); }

    @PutMapping("/update")
    public Hotel updateHotel(@RequestBody @Valid Hotel hotel) { return service.updateHotel(hotel); }

    @GetMapping("/getbyid/{hotelId}")
    public Hotel getByHotelId(@PathVariable Long hotelId) { return service.getByHotelId(hotelId); }

    @DeleteMapping("/deletebyid/{hotelId}")
    public String deleteHotel(@PathVariable Long hotelId) { return service.deleteByHotelId(hotelId); }

    @GetMapping("/getall")
    public List<Hotel> getAll() { return service.getAllHotels(); }

    @GetMapping("/search/location/{q}")
    public List<Hotel> search(@PathVariable String q) { return service.searchByLocation(q); }
}
