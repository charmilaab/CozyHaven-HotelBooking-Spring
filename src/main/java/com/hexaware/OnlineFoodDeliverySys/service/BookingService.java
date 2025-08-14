package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingDto bookingDto);
    Booking updateBooking(BookingDto bookingDto);
    List<Booking> getAllBookings();
    Booking getBookingById(Long bookingId);
    void deleteBooking(Long bookingId);
}
