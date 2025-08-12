package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.repository.BookingRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.RoomRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public Booking addBooking(BookingDto dto) {
        Booking booking = new Booking();
        booking.setBookingId(dto.getBookingId());
        booking.setUser(userRepo.findById(dto.getUserId()).orElse(null));
        booking.setRoom(roomRepo.findById(dto.getRoomId()).orElse(null));
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setNumberOfRooms(dto.getNumberOfRooms());
        booking.setTotalAmount(dto.getTotalAmount());
        booking.setStatus(dto.getStatus());
        return bookingRepo.save(booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    @Override
    public Booking getByBookingId(Long bookingId) {
        return bookingRepo.findById(bookingId).orElse(null);
    }

    @Override
    public String deleteByBookingId(Long bookingId) {
        bookingRepo.deleteById(bookingId);
        return "Booking deleted successfully";
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepo.findBookingsByUser(userId);
    }
}
