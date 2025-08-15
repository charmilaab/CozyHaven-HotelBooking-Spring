package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.HotelDto;
import com.hexaware.hotelbooking.entities.Hotel;
import com.hexaware.hotelbooking.exceptions.HotelNotFoundException;
import com.hexaware.hotelbooking.repository.HotelRepository;
import com.hexaware.hotelbooking.service.HotelServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

    @Mock
    private HotelRepository repo;

    @InjectMocks
    private HotelServiceImpl service;

    private Hotel hotel;
    private HotelDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setHotelId(101L);
        hotel.setName("Charu Palace");
        hotel.setLocation("Chennai");
        hotel.setDescription("Luxury hotel with sea view");
        hotel.setAmenities("WiFi, Pool, Spa");

        dto = new HotelDto();
        dto.setHotelId(101L);
        dto.setName("Charu Palace");
        dto.setLocation("Chennai");
        dto.setDescription("Luxury hotel with sea view");
        dto.setAmenities("WiFi, Pool, Spa");
    }

    @Test
    void testAddHotel() {
        when(repo.save(any(Hotel.class))).thenReturn(hotel);
        Hotel saved = service.addHotel(dto);
        assertEquals("Charu Palace", saved.getName());
        verify(repo, times(1)).save(any(Hotel.class));
    }

    @Test
    void testUpdateHotel() {
        when(repo.save(any(Hotel.class))).thenReturn(hotel);
        Hotel updated = service.updateHotel(hotel);
        assertEquals("Charu Palace", updated.getName());
        verify(repo, times(1)).save(hotel);
    }

    @Test
    void testGetByHotelId_Found() {
        when(repo.findById(101L)).thenReturn(Optional.of(hotel));
        Hotel found = service.getByHotelId(101L);
        assertNotNull(found);
        assertEquals("Charu Palace", found.getName());
    }

    @Test
    void testGetByHotelId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(HotelNotFoundException.class, () -> service.getByHotelId(999L));
    }

    @Test
    void testDeleteHotel() {
        when(repo.findById(101L)).thenReturn(Optional.of(hotel));
        String message = service.deleteHotel(101L);
        assertEquals("Hotel deleted successfully", message);
        verify(repo, times(1)).deleteById(101L);
    }

    @Test
    void testGetAllHotels() {
        when(repo.findAll()).thenReturn(Arrays.asList(hotel));
        assertEquals(1, service.getAllHotels().size());
    }

    @Test
    void testSearchHotelsByLocation() {
        when(repo.searchHotelsByLocation("Chennai")).thenReturn(Arrays.asList(hotel));
        assertEquals(1, service.searchHotelsByLocation("Chennai").size());
    }
}
