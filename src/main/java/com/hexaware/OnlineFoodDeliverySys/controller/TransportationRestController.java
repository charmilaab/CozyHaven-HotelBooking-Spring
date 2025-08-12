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
@RequestMapping("/api/transportations")
public class TransportationRestController {

    @Autowired
    private TransportationService service;

    @PostMapping("/insert")
    public Transportation addTransportation(@RequestBody @Valid TransportationDto dto) {
        log.info("Received request to add transportation: {}", dto);
        return service.addTransportation(dto);
    }

    @PutMapping("/update")
    public Transportation updateTransportation(@RequestBody @Valid Transportation transportation) {
        log.info("Received request to update transportation: {}", transportation);
        return service.updateTransportation(transportation);
    }

    @GetMapping("/getbyid/{transportId}")
    public Transportation getByTransportId(@PathVariable Long transportId) {
        log.info("Received request to fetch transportation with ID: {}", transportId);
        return service.getByTransportId(transportId);
    }

    @DeleteMapping("/deletebyid/{transportId}")
    public String deleteByTransportId(@PathVariable Long transportId) {
        log.info("Received request to delete transportation with ID: {}", transportId);
        return service.deleteByTransportId(transportId);
    }

    @GetMapping("/getall")
    public List<Transportation> getAllTransportations() {
        log.info("Received request to fetch all transportations");
        return service.getAllTransport();
    }
}
