package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.exceptions.HotelNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.service.HotelService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hotels")
public class HotelRestController {

    @Autowired
    private HotelService hotelService;

    // Create new hotel
    @PostMapping("/insert")
    public ResponseEntity<Hotel> addHotel(@Valid @RequestBody HotelDto dto) {
        Hotel createdHotel = hotelService.addHotel(dto);
        return ResponseEntity.ok(createdHotel);
    }

    // Update existing hotel
    @PutMapping("/update")
    public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel) {
        try {
            Hotel updatedHotel = hotelService.updateHotel(hotel);
            return ResponseEntity.ok(updatedHotel);
        } catch (HotelNotFoundException ex) {
            log.error("Update failed: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Get hotel by ID
    @GetMapping("/getbyid/{hotelId}")
    public ResponseEntity<Hotel> getByHotelId(@PathVariable Long hotelId) {
        try {
            Hotel hotel = hotelService.getByHotelId(hotelId);
            return ResponseEntity.ok(hotel);
        } catch (HotelNotFoundException ex) {
            log.error("Hotel not found: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Delete hotel by ID
    @DeleteMapping("/deletebyid/{hotelId}")
    public ResponseEntity<String> deleteByHotelId(@PathVariable Long hotelId) {
        try {
            String msg = hotelService.deleteByHotelId(hotelId);
            return ResponseEntity.ok(msg);
        } catch (HotelNotFoundException ex) {
            log.error("Delete failed: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Get all hotels
    @GetMapping("/getall")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hotels);
    }

    // Search hotels by location (query param)
    @GetMapping("/searchbylocation")
    public ResponseEntity<List<Hotel>> searchByLocation(@RequestParam String location) {
        List<Hotel> hotels = hotelService.searchByLocation(location);
        if (hotels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hotels);
    }
}
