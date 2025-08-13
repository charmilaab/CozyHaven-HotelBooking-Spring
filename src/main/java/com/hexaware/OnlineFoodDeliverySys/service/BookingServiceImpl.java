package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.*;
import com.hexaware.OnlineFoodDeliverySys.exceptions.*;
import com.hexaware.OnlineFoodDeliverySys.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired private BookingRepository repo;
    @Autowired private UserRepository userRepo;
    @Autowired private RoomRepository roomRepo;

    @Override
    public Booking addBooking(BookingDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + dto.getUserId()));
        Room room = roomRepo.findById(dto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + dto.getRoomId()));

        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }

        Booking b = new Booking();
        b.setBookingId(dto.getBookingId());
        b.setUser(user);
        b.setRoom(room);
        b.setCheckInDate(dto.getCheckInDate());
        b.setCheckOutDate(dto.getCheckOutDate());
        b.setNumberOfRooms(dto.getNumberOfRooms());
        b.setTotalAmount(dto.getTotalAmount());
        b.setStatus(dto.getStatus());
        return repo.save(b);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        repo.findById(booking.getBookingId())
            .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + booking.getBookingId()));
        return repo.save(booking);
    }

    @Override
    public Booking getByBookingId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + id));
    }

    @Override
    public String deleteByBookingId(Long id) {
        Booking b = getByBookingId(id);
        repo.delete(b);
        return "Booking deleted successfully";
    }

    @Override
    public List<Booking> getAllBookings() { return repo.findAll(); }

    @Override
    public List<Booking> getBookingsByUser(Long userId) { return repo.findBookingsByUser(userId); }
}
