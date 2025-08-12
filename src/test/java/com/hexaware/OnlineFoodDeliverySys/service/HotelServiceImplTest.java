package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.exceptions.HotelNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
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
        hotel.setDescription("Luxury stay");
        hotel.setAmenities("WiFi, Pool");

        dto = new HotelDto();
        dto.setHotelId(101L);
        dto.setName("Charu Palace");
        dto.setLocation("Chennai");
        dto.setDescription("Luxury stay");
        dto.setAmenities("WiFi, Pool");
    }

    @Test
    void testAddHotel() {
        when(repo.save(any(Hotel.class))).thenReturn(hotel);
        Hotel saved = service.addHotel(dto);
        assertEquals("Charu Palace", saved.getName());
    }

    @Test
    void testGetByHotelId_Found() {
        when(repo.findById(101L)).thenReturn(Optional.of(hotel));
        assertEquals("Charu Palace", service.getByHotelId(101L).getName());
    }

    @Test
    void testGetByHotelId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(HotelNotFoundException.class, () -> service.getByHotelId(999L));
    }

    @Test
    void testGetAllHotels() {
        when(repo.findAll()).thenReturn(Arrays.asList(hotel));
        assertEquals(1, service.getAllHotels().size());
    }

    @Test
    void testDeleteByHotelId() {
        when(repo.findById(101L)).thenReturn(Optional.of(hotel));
        String result = service.deleteByHotelId(101L);
        assertEquals("Hotel deleted successfully", result);
    }
}
