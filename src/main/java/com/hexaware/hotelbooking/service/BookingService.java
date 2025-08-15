package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.BookingDto;
import com.hexaware.hotelbooking.entities.Booking;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingDto bookingDto);
    Booking updateBooking(BookingDto bookingDto);
    List<Booking> getAllBookings();
    Booking getBookingById(Long bookingId);
    void deleteBooking(Long bookingId);
}
