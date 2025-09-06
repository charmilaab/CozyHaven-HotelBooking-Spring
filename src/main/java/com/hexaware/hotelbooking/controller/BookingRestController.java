package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.BookingDto;
import com.hexaware.hotelbooking.service.BookingService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    // Insert booking (bookingId auto-generated)
    @PostMapping("/insert")
    public BookingDto add(@Valid @RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto);
    }

    // Update booking (bookingId required)
    @PutMapping("/update")
    public BookingDto update(@Valid @RequestBody BookingDto bookingDto) {
        if (bookingDto.getBookingId() == null) {
            throw new IllegalArgumentException("Booking ID is required for update");
        }
        return bookingService.updateBooking(bookingDto);
    }

    @GetMapping("/getall")
    public List<BookingDto> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/get/{id}")
    public BookingDto getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted with ID: " + id;
    }

    @GetMapping("/getbyuser/{userId}")
    public List<BookingDto> getByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }
}
