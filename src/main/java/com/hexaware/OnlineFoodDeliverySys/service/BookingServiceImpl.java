package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.BookingNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.BookingRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Override
    public Booking addBooking(BookingDto dto) {
        Booking booking = new Booking();
        booking.setBookingId(dto.getBookingId());

        User user = new User();
        user.setUserId(dto.getUser().getUserId());
        booking.setUser(user);

        Room room = new Room();
        room.setRoomId(dto.getRoom().getRoomId());
        booking.setRoom(room);

        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setNumberOfRooms(dto.getNumberOfRooms());
        booking.setTotalAmount(dto.getTotalAmount());
        booking.setStatus(dto.getStatus());

        log.info("Adding new booking: {}", dto);
        return bookingRepo.save(booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        if (!bookingRepo.existsById(booking.getBookingId())) {
            throw new BookingNotFoundException("Booking not found with ID: " + booking.getBookingId());
        }
        return bookingRepo.save(booking);
    }

    @Override
    public Booking getByBookingId(Long bookingId) {
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
    }

    @Override
    public String deleteByBookingId(Long bookingId) {
        if (!bookingRepo.existsById(bookingId)) {
            throw new BookingNotFoundException("Cannot delete. Booking not found with ID: " + bookingId);
        }
        bookingRepo.deleteById(bookingId);
        return "Booking deleted successfully";
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public List<Booking> getByUserId(Long userId) {
        return bookingRepo.findBookingsByUser(userId);
    }
}
