package com.hexaware.hotelbooking.service;
import com.hexaware.hotelbooking.dto.BookingDto;
import java.util.List;

public interface BookingService {
    BookingDto addBooking(BookingDto bookingDto);
    BookingDto updateBooking(BookingDto bookingDto);
    List<BookingDto> getAllBookings();
    BookingDto getBookingById(Long bookingId);
    void deleteBooking(Long bookingId);
    List<BookingDto> getBookingsByUser(Long userId);
}
