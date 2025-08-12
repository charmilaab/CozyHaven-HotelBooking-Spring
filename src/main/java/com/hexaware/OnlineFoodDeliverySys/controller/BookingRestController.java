package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.service.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService service;

    @PostMapping("/insert")
    public Booking addBooking(@RequestBody @Valid BookingDto dto) {
        log.info("Received request to add booking: {}", dto);
        return service.addBooking(dto);
    }

    @PutMapping("/update")
    public Booking updateBooking(@RequestBody @Valid Booking booking) {
        log.info("Received request to update booking: {}", booking);
        return service.updateBooking(booking);
    }

    @GetMapping("/getbyid/{bookingId}")
    public Booking getByBookingId(@PathVariable Long bookingId) {
        log.info("Received request to fetch booking with ID: {}", bookingId);
        return service.getByBookingId(bookingId);
    }

    @DeleteMapping("/deletebyid/{bookingId}")
    public String deleteByBookingId(@PathVariable Long bookingId) {
        log.info("Received request to delete booking with ID: {}", bookingId);
        return service.deleteByBookingId(bookingId);
    }

    @GetMapping("/getall")
    public List<Booking> getAllBookings() {
        log.info("Received request to fetch all bookings");
        return service.getAllBookings();
    }
}
