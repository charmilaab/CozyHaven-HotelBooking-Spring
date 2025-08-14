package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.BookingNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.BookingRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.RoomRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoomRepository roomRepo;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private Room room;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup sample user
        user = new User();
        user.setUserId(1L);

        // Setup sample room
        room = new Room();
        room.setRoomId(1L);
        room.setBaseFare(2000.0);

        // Setup sample bookingDto (no totalAmount so it will be calculated)
        bookingDto = new BookingDto(
                null, // bookingId is null for add
                1L,
                1L,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3),
                2, // numberOfRooms
                null, // totalAmount auto-calculated
                "CONFIRMED"
        );
    }

    @Test
    void testAddBooking() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking savedBooking = bookingService.addBooking(bookingDto);

        // Nights = 2 days, Rooms = 2, Base Fare = 2000 → 2 nights × 2 rooms × 2000 = 8000
        assertEquals(8000.0, savedBooking.getTotalAmount());
        assertEquals("CONFIRMED", savedBooking.getStatus());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking() {
        Booking existing = new Booking();
        existing.setBookingId(10L);
        existing.setNumberOfRooms(1);

        BookingDto updateDto = new BookingDto(
                10L,
                1L,
                1L,
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(7),
                3, // Updated numberOfRooms
                null, // Auto calculate
                "UPDATED"
        );

        when(bookingRepo.findById(10L)).thenReturn(Optional.of(existing));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepo.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking updatedBooking = bookingService.updateBooking(updateDto);

        // Nights = 2, Rooms = 3, Base Fare = 2000 → 2 × 3 × 2000 = 12000
        assertEquals(12000.0, updatedBooking.getTotalAmount());
        assertEquals(3, updatedBooking.getNumberOfRooms());
        assertEquals("UPDATED", updatedBooking.getStatus());
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetBookingById_NotFound() {
        when(bookingRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(99L));
    }
}
