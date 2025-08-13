package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import com.hexaware.OnlineFoodDeliverySys.service.TransportationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/transport")
public class TransportationRestController {

    @Autowired private TransportationService service;

    @PostMapping("/insert")
    public Transportation add(@RequestBody @Valid TransportationDto dto) { return service.addTransport(dto); }

    @PutMapping("/update")
    public Transportation update(@RequestBody @Valid Transportation transport) { return service.updateTransport(transport); }

    @GetMapping("/getbyid/{id}")
    public Transportation get(@PathVariable Long id) { return service.getByTransportId(id); }

    @DeleteMapping("/deletebyid/{id}")
    public String delete(@PathVariable Long id) { return service.deleteByTransportId(id); }

    @GetMapping("/getall")
    public List<Transportation> all() { return service.getAllTransport(); }

    @GetMapping("/byhotel/{hotelId}")
    public List<Transportation> byHotel(@PathVariable Long hotelId) { return service.getTransportByHotel(hotelId); }
}
