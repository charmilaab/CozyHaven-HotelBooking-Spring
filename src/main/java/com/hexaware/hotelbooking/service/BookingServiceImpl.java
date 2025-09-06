package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.BookingDto;
import com.hexaware.hotelbooking.entities.Booking;
import com.hexaware.hotelbooking.entities.Room;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.entities.Transportation;
import com.hexaware.hotelbooking.exceptions.BookingNotFoundException;
import com.hexaware.hotelbooking.exceptions.RoomNotFoundException;
import com.hexaware.hotelbooking.exceptions.UserNotFoundException;
import com.hexaware.hotelbooking.repository.BookingRepository;
import com.hexaware.hotelbooking.repository.RoomRepository;
import com.hexaware.hotelbooking.repository.UserRepository;
import com.hexaware.hotelbooking.repository.TransportationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private TransportationRepository transportRepo;

    private BookingDto convertToDto(Booking booking) {
        return new BookingDto(
            booking.getBookingId(),
            booking.getTransport() != null ? booking.getTransport().getTransportId() : null,
            booking.getTransport(), // pass full transport object
            booking.getUser().getUserId(),
            booking.getRoom().getRoomId(),
            booking.getCheckInDate(),
            booking.getCheckOutDate(),
            booking.getNumberOfRooms(),
            booking.getTotalAmount(),
            booking.getStatus()
        );
    }


    @Override
    public BookingDto addBooking(BookingDto bookingDto) {
        User user = userRepo.findById(bookingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + bookingDto.getUserId()));

        Room room = roomRepo.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + bookingDto.getRoomId()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setCheckOutDate(bookingDto.getCheckOutDate());

        // ✅ Optional Transport
        if (bookingDto.getTransportId() != null) {
            Transportation transport = transportRepo.findById(bookingDto.getTransportId())
                    .orElseThrow(() -> new RuntimeException("Transport not found with ID: " + bookingDto.getTransportId()));
            booking.setTransport(transport);
        }

        int numRooms = bookingDto.getNumberOfRooms() != null ? bookingDto.getNumberOfRooms() : 1;
        booking.setNumberOfRooms(numRooms);

        if (bookingDto.getTotalAmount() != null) {
            booking.setTotalAmount(bookingDto.getTotalAmount());
        } else {
            long nights = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
            double total = room.getBaseFare() * numRooms * nights;

            if (booking.getTransport() != null) {
                total += booking.getTransport().getCost();
            }
            booking.setTotalAmount(total);
        }

        booking.setStatus(bookingDto.getStatus());

        booking = bookingRepo.save(booking);
        return convertToDto(booking);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
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

        // ✅ Update transport
        if (bookingDto.getTransportId() != null) {
            Transportation transport = transportRepo.findById(bookingDto.getTransportId())
                    .orElseThrow(() -> new RuntimeException("Transport not found with ID: " + bookingDto.getTransportId()));
            existing.setTransport(transport);
        } else {
            existing.setTransport(null);
        }

        if (bookingDto.getNumberOfRooms() != null) {
            existing.setNumberOfRooms(bookingDto.getNumberOfRooms());
        }

        if (bookingDto.getTotalAmount() != null) {
            existing.setTotalAmount(bookingDto.getTotalAmount());
        } else {
            long nights = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
            int numRooms = existing.getNumberOfRooms() != null ? existing.getNumberOfRooms() : 1;
            double total = room.getBaseFare() * numRooms * nights;

            if (existing.getTransport() != null) {
                total += existing.getTransport().getCost();
            }
            existing.setTotalAmount(total);
        }

        existing.setStatus(bookingDto.getStatus());

        existing = bookingRepo.save(existing);
        return convertToDto(existing);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto getBookingById(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
        return convertToDto(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
        bookingRepo.delete(booking);
    }

    @Override
    public List<BookingDto> getBookingsByUser(Long userId) {
        return bookingRepo.findByUserUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
