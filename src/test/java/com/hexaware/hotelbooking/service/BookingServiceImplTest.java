package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.BookingDto;
import com.hexaware.hotelbooking.entities.Booking;
import com.hexaware.hotelbooking.entities.Room;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.entities.Transportation;
import com.hexaware.hotelbooking.exceptions.BookingNotFoundException;
import com.hexaware.hotelbooking.repository.BookingRepository;
import com.hexaware.hotelbooking.repository.RoomRepository;
import com.hexaware.hotelbooking.repository.UserRepository;
import com.hexaware.hotelbooking.repository.TransportationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock private BookingRepository bookingRepo;
    @Mock private UserRepository userRepo;
    @Mock private RoomRepository roomRepo;
    @Mock private TransportationRepository transportRepo;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private Room room;
    private Transportation transport;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        // Sample user
        user = new User();
        user.setUserId(1L);

        // Sample room
        room = new Room();
        room.setRoomId(1L);
        room.setBaseFare(2000.0);

        // Sample transport
        transport = new Transportation();
        transport.setTransportId(1L);
        transport.setType("Bus"); // optional type for clarity
        transport.setCost(500.0);

        // BookingDto must follow the declared order in BookingDto.java
        bookingDto = new BookingDto(
                null,      // bookingId
                1L,        // transportId
                transport, // full transport object
                1L,        // userId
                1L,        // roomId
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3),
                2,         // numberOfRooms
                null,      // totalAmount auto-calculated
                "CONFIRMED"
        );
    }

    @Test
    void testAddBooking() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(room));
        when(transportRepo.findById(1L)).thenReturn(Optional.of(transport));
        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingDto savedBooking = bookingService.addBooking(bookingDto);

        // Nights = 2 × Rooms = 2 × 2000 = 4000 × 2 nights = 8000 + transport 500 = 8500
        assertEquals(8500.0, savedBooking.getTotalAmount());
        assertEquals("CONFIRMED", savedBooking.getStatus());
        assertNotNull(savedBooking.getTransport());
        assertEquals(1L, savedBooking.getTransport().getTransportId());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking() {
        Booking existing = new Booking();
        existing.setBookingId(10L);
        existing.setNumberOfRooms(1);

        BookingDto updateDto = new BookingDto(
                10L,        // bookingId
                1L,         // transportId
                transport,  // full transport object
                1L,         // userId
                1L,         // roomId
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(7),
                3,          // updated rooms
                null,       // totalAmount auto-calculated
                "UPDATED"
        );

        when(bookingRepo.findById(10L)).thenReturn(Optional.of(existing));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(room));
        when(transportRepo.findById(1L)).thenReturn(Optional.of(transport));
        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingDto updatedBooking = bookingService.updateBooking(updateDto);

        // Nights = 2 × Rooms = 3 × 2000 × 2 nights = 12000 + transport 500 = 12500
        assertEquals(12500.0, updatedBooking.getTotalAmount());
        assertEquals(3, updatedBooking.getNumberOfRooms());
        assertEquals("UPDATED", updatedBooking.getStatus());
        assertNotNull(updatedBooking.getTransport());
        assertEquals("Bus", updatedBooking.getTransport().getType());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetBookingById_NotFound() {
        when(bookingRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(99L));
    }
}
