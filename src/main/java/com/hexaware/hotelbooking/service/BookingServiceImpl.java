package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.BookingDto;
import com.hexaware.hotelbooking.entities.Booking;
import com.hexaware.hotelbooking.entities.Room;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.exceptions.BookingNotFoundException;
import com.hexaware.hotelbooking.exceptions.RoomNotFoundException;
import com.hexaware.hotelbooking.exceptions.UserNotFoundException;
import com.hexaware.hotelbooking.repository.BookingRepository;
import com.hexaware.hotelbooking.repository.RoomRepository;
import com.hexaware.hotelbooking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
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
    public Booking addBooking(BookingDto bookingDto) {
        User user = userRepo.findById(bookingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + bookingDto.getUserId()));

        Room room = roomRepo.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + bookingDto.getRoomId()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setCheckOutDate(bookingDto.getCheckOutDate());

        int numRooms = bookingDto.getNumberOfRooms() != null ? bookingDto.getNumberOfRooms() : 1;
        booking.setNumberOfRooms(numRooms);

        if (bookingDto.getTotalAmount() != null) {
            booking.setTotalAmount(bookingDto.getTotalAmount());
        } else {
            long nights = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
            booking.setTotalAmount(room.getBaseFare() * numRooms * nights);
        }

        booking.setStatus(bookingDto.getStatus());

        return bookingRepo.save(booking);
    }

    @Override
    public Booking updateBooking(BookingDto bookingDto) {
        Booking existing = bookingRepo.findById(bookingDto.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingDto.getBookingId()));

        User user = userRepo.findById(bookingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + bookingDto.getUserId()));

        Room room = roomRepo.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + bookingDto.getRoomId()));

        existing.setUser(user);
        existing.setRoom(room);
        existing.setCheckInDate(bookingDto.getCheckInDate());
        existing.setCheckOutDate(bookingDto.getCheckOutDate());

        if (bookingDto.getNumberOfRooms() != null) {
            existing.setNumberOfRooms(bookingDto.getNumberOfRooms());
        }

        if (bookingDto.getTotalAmount() != null) {
            existing.setTotalAmount(bookingDto.getTotalAmount());
        } else {
            long nights = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
            int numRooms = existing.getNumberOfRooms() != null ? existing.getNumberOfRooms() : 1;
            existing.setTotalAmount(room.getBaseFare() * numRooms * nights);
        }

        existing.setStatus(bookingDto.getStatus());

        return bookingRepo.save(existing);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
        bookingRepo.delete(booking);
    }
}
