package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.RoomDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Room;
import com.hexaware.OnlineFoodDeliverySys.exceptions.RoomNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepository repo;

    @InjectMocks
    private RoomServiceImpl service;

    private Room room;
    private RoomDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room();
        room.setRoomId(301L);
        room.setRoomType("Deluxe");
        room.setBaseFare(2500.0);
        room.setMaxOccupancy(2);
        room.setBedType("King");
        room.setSize("300 sqft");

        dto = new RoomDto();
        dto.setRoomId(301L);
        dto.setRoomType("Deluxe");
        dto.setBaseFare(2500.0);
        dto.setMaxOccupancy(2);
        dto.setBedType("King");
        dto.setSize("300 sqft");
    }

    @Test
    void testAddRoom() {
        when(repo.save(any(Room.class))).thenReturn(room);
        Room saved = service.addRoom(dto);
        assertEquals("Deluxe", saved.getRoomType());
    }

    @Test
    void testGetByRoomId_Found() {
        when(repo.findById(301L)).thenReturn(Optional.of(room));
        assertEquals("Deluxe", service.getByRoomId(301L).getRoomType());
    }

    @Test
    void testGetByRoomId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RoomNotFoundException.class, () -> service.getByRoomId(999L));
    }

    @Test
    void testGetAllRooms() {
        when(repo.findAll()).thenReturn(Arrays.asList(room));
        assertEquals(1, service.getAllRooms().size());
    }

    @Test
    void testDeleteByRoomId() {
        when(repo.findById(301L)).thenReturn(Optional.of(room));
        String result = service.deleteByRoomId(301L);
        assertEquals("Room deleted successfully", result);
    }
}
