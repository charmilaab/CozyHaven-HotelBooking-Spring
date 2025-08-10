package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;

public interface BookingService {
    Booking addBooking(BookingDto dto);
    Booking updateBooking(Booking booking);
    Booking getByBookingId(Long bookingId);
    String deleteByBookingId(Long bookingId);
    List<Booking> getAllBookings();
    List<Booking> getByUserId(Long userId);
}
