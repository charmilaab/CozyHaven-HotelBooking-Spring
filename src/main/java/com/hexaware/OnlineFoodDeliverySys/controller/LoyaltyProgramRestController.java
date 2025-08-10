package com.hexaware.OnlineFoodDeliverySys.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.OnlineFoodDeliverySys.dto.LoyaltyProgramDto;
import com.hexaware.OnlineFoodDeliverySys.entities.LoyaltyProgram;
import com.hexaware.OnlineFoodDeliverySys.service.LoyaltyProgramService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/loyaltyprograms")
public class LoyaltyProgramRestController {

    @Autowired
    private LoyaltyProgramService service;

    @PostMapping("/insert")
    public LoyaltyProgram addLoyaltyProgram(@RequestBody @Valid LoyaltyProgramDto dto) {
        return service.addLoyaltyProgram(dto);
    }

    @PutMapping("/update")
    public LoyaltyProgram updateLoyaltyProgram(@RequestBody LoyaltyProgram loyaltyProgram) {
        return service.updateLoyaltyProgram(loyaltyProgram);
    }

    @GetMapping("/getbyid/{loyaltyId}")
    public LoyaltyProgram getByLoyaltyId(@PathVariable Long loyaltyId) {
        return service.getByLoyaltyId(loyaltyId);
    }

    @DeleteMapping("/deletebyid/{loyaltyId}")
    public String deleteByLoyaltyId(@PathVariable Long loyaltyId) {
        return service.deleteByLoyaltyId(loyaltyId);
    }

    @GetMapping("/getbyuserid/{userId}")
    public Optional<LoyaltyProgram> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @GetMapping("/getall")
    public List<LoyaltyProgram> getAllLoyaltyPrograms() {
        return service.getAllLoyaltyPrograms();
    }
}
