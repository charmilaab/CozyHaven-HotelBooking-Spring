package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.BookingDto;
import com.hexaware.hotelbooking.entities.Booking;
import com.hexaware.hotelbooking.service.BookingService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    // Insert booking (bookingId auto-generated)
    @PostMapping("/insert")
    public Booking add(@Valid @RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto);
    }

    // Update booking (bookingId required)
    @PutMapping("/update")
    public Booking update(@Valid @RequestBody BookingDto bookingDto) {
        if (bookingDto.getBookingId() == null) {
            throw new IllegalArgumentException("Booking ID is required for update");
        }
        return bookingService.updateBooking(bookingDto);
    }

    @GetMapping("/getall")
    public List<Booking> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/get/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted with ID: " + id;
    }
}
