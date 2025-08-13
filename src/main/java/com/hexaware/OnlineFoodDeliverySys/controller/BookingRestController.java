package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired private BookingService service;

    @PostMapping("/insert")
    public Booking add(@RequestBody @Valid BookingDto dto) { return service.addBooking(dto); }

    @PutMapping("/update")
    public Booking update(@RequestBody @Valid Booking booking) { return service.updateBooking(booking); }

    @GetMapping("/getbyid/{bookingId}")
    public Booking get(@PathVariable Long bookingId) { return service.getByBookingId(bookingId); }

    @DeleteMapping("/deletebyid/{bookingId}")
    public String delete(@PathVariable Long bookingId) { return service.deleteByBookingId(bookingId); }

    @GetMapping("/getall")
    public List<Booking> all() { return service.getAllBookings(); }

    @GetMapping("/byuser/{userId}")
    public List<Booking> byUser(@PathVariable Long userId) { return service.getBookingsByUser(userId); }
}
