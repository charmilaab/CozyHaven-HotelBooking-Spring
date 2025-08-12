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
        return service.addBooking(dto);
    }

    @PutMapping("/update")
    public Booking updateBooking(@RequestBody Booking booking) {
        return service.updateBooking(booking);
    }

    @GetMapping("/getbyid/{bookingId}")
    public Booking getByBookingId(@PathVariable Long bookingId) {
        return service.getByBookingId(bookingId);
    }

    @DeleteMapping("/deletebyid/{bookingId}")
    public String deleteByBookingId(@PathVariable Long bookingId) {
        return service.deleteByBookingId(bookingId);
    }

    @GetMapping("/getbyuser/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return service.getBookingsByUser(userId);
    }

    @GetMapping("/getall")
    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }
}
