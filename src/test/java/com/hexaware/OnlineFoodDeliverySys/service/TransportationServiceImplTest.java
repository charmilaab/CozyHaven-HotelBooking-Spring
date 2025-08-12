package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import com.hexaware.OnlineFoodDeliverySys.repository.TransportationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransportationServiceImplTest {

    @Mock
    private TransportationRepository repo;

    @InjectMocks
    private TransportationServiceImpl service;

    private Transportation transport;
    private TransportationDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transport = new Transportation();
        transport.setTransportId(601L);
        transport.setType("Airport Pickup");
        transport.setDetails("Available 24/7");
        transport.setCost(1200.0);

        dto = new TransportationDto();
        dto.setTransportId(601L);
        dto.setType("Airport Pickup");
        dto.setDetails("Available 24/7");
        dto.setCost(1200.0);
        dto.setHotelId(101L); 
    }

    @Test
    void testAddTransport() {
        when(repo.save(any(Transportation.class))).thenReturn(transport);
        Transportation saved = service.addTransport(dto);
        assertEquals("Airport Pickup", saved.getType());
        verify(repo, times(1)).save(any(Transportation.class));
    }

    @Test
    void testGetByTransportId_Found() {
        when(repo.findById(601L)).thenReturn(Optional.of(transport));
        Transportation found = service.getByTransportId(601L);
        assertNotNull(found);
        assertEquals("Airport Pickup", found.getType());
    }

    @Test
    void testGetByTransportId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        Transportation found = service.getByTransportId(999L);
        assertNull(found); 
    }

    @Test
    void testGetAllTransport() {
        when(repo.findAll()).thenReturn(Arrays.asList(transport));
        assertEquals(1, service.getAllTransport().size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testDeleteByTransportId() {
        doNothing().when(repo).deleteById(601L);
        String result = service.deleteByTransportId(601L);
        assertEquals("Transportation deleted successfully", result);
        verify(repo, times(1)).deleteById(601L);
    }
}
