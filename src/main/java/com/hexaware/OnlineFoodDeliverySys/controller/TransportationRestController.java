package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import com.hexaware.OnlineFoodDeliverySys.exceptions.TransportationNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.service.TransportationService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transportations")
public class TransportationRestController {

    @Autowired
    private TransportationService transportationService;

    // Create new transportation
    @PostMapping("/insert")
    public ResponseEntity<Transportation> addTransportation(@Valid @RequestBody TransportationDto dto) {
        Transportation created = transportationService.addTransportation(dto);
        return ResponseEntity.ok(created);
    }

    // Update existing transportation
    @PutMapping("/update")
    public ResponseEntity<?> updateTransportation(@RequestBody Transportation transportation) {
        try {
            Transportation updated = transportationService.updateTransportation(transportation);
            return ResponseEntity.ok(updated);
        } catch (TransportationNotFoundException ex) {
            log.error("Update failed: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Get transportation by ID
    @GetMapping("/getbyid/{transportId}")
    public ResponseEntity<Transportation> getByTransportationId(@PathVariable Long transportId) {
        try {
            Transportation transportation = transportationService.getByTransportationId(transportId);
            return ResponseEntity.ok(transportation);
        } catch (TransportationNotFoundException ex) {
            log.error("Transportation not found: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Delete transportation by ID
    @DeleteMapping("/deletebyid/{transportId}")
    public ResponseEntity<String> deleteByTransportationId(@PathVariable Long transportId) {
        try {
            String msg = transportationService.deleteByTransportationId(transportId);
            return ResponseEntity.ok(msg);
        } catch (TransportationNotFoundException ex) {
            log.error("Delete failed: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Get all transportations
    @GetMapping("/getall")
    public ResponseEntity<List<Transportation>> getAllTransportations() {
        List<Transportation> list = transportationService.getAllTransportations();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // Get transport options by hotel ID
    @GetMapping("/byhotel/{hotelId}")
    public ResponseEntity<List<Transportation>> getByHotelId(@PathVariable Long hotelId) {
        List<Transportation> list = transportationService.getByHotelId(hotelId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}
