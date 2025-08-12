package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import com.hexaware.OnlineFoodDeliverySys.service.TransportationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transport")
public class TransportationRestController {

    @Autowired
    private TransportationService service;

    @PostMapping("/insert")
    public Transportation addTransport(@RequestBody @Valid TransportationDto dto) {
        return service.addTransport(dto);
    }

    @PutMapping("/update")
    public Transportation updateTransport(@RequestBody Transportation transport) {
        return service.updateTransport(transport);
    }

    @GetMapping("/getbyid/{transportId}")
    public Transportation getByTransportId(@PathVariable Long transportId) {
        return service.getByTransportId(transportId);
    }

    @DeleteMapping("/deletebyid/{transportId}")
    public String deleteByTransportId(@PathVariable Long transportId) {
        return service.deleteByTransportId(transportId);
    }

    @GetMapping("/getbyhotel/{hotelId}")
    public List<Transportation> getTransportByHotel(@PathVariable Long hotelId) {
        return service.getTransportByHotel(hotelId);
    }

    @GetMapping("/getall")
    public List<Transportation> getAllTransport() {
        return service.getAllTransport();
    }
}
