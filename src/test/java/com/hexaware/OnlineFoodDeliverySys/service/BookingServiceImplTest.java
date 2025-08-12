package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.BookingDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.exceptions.BookingNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepository repo;

    @InjectMocks
    private BookingServiceImpl service;

    private Booking booking;
    private BookingDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new Booking();
        booking.setBookingId(201L);
        booking.setCheckInDate(LocalDate.of(2025, 8, 10));
        booking.setCheckOutDate(LocalDate.of(2025, 8, 15));
        booking.setNumberOfRooms(2);
        booking.setTotalAmount(5000.0);
        booking.setStatus("CONFIRMED");

        dto = new BookingDto();
        dto.setBookingId(201L);
        dto.setCheckInDate(LocalDate.of(2025, 8, 10));
        dto.setCheckOutDate(LocalDate.of(2025, 8, 15));
        dto.setNumberOfRooms(2);
        dto.setTotalAmount(5000.0);
        dto.setStatus("CONFIRMED");
    }

    @Test
    void testAddBooking() {
        when(repo.save(any(Booking.class))).thenReturn(booking);
        Booking saved = service.addBooking(dto);
        assertEquals("CONFIRMED", saved.getStatus());
    }

    @Test
    void testGetByBookingId_Found() {
        when(repo.findById(201L)).thenReturn(Optional.of(booking));
        assertEquals("CONFIRMED", service.getByBookingId(201L).getStatus());
    }

    @Test
    void testGetByBookingId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> service.getByBookingId(999L));
    }

    @Test
    void testGetAllBookings() {
        when(repo.findAll()).thenReturn(Arrays.asList(booking));
        assertEquals(1, service.getAllBookings().size());
    }

    @Test
    void testDeleteByBookingId() {
        when(repo.findById(201L)).thenReturn(Optional.of(booking));
        String result = service.deleteByBookingId(201L);
        assertEquals("Booking deleted successfully", result);
    }
}
