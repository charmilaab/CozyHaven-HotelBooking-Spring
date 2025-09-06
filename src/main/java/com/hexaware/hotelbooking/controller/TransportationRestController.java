package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.TransportationDto;
import com.hexaware.hotelbooking.entities.Transportation;
import com.hexaware.hotelbooking.service.TransportationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@RequestMapping("/api/transport")
@CrossOrigin(origins = "http://localhost:5173") 
public class TransportationRestController {

    @Autowired private TransportationService service;

    @PostMapping("/insert")
    public Transportation add(@RequestBody @Valid TransportationDto dto) { return service.addTransport(dto); }

    @PutMapping("/update")
    public Transportation update(@RequestBody @Valid TransportationDto dto) { return service.updateTransport(dto); }

    @GetMapping("/getbyid/{id}")
    public Transportation get(@PathVariable Long id) { return service.getByTransportId(id); }

    @DeleteMapping("/deletebyid/{id}")
    public String delete(@PathVariable Long id) { return service.deleteByTransportId(id); }

    @GetMapping("/getall")
    public List<Transportation> all() { return service.getAllTransport(); }

    @GetMapping("/getbyhotel/{hotelId}")
    public List<Transportation> byHotel(@PathVariable Long hotelId) { return service.getTransportByHotel(hotelId); }
}
