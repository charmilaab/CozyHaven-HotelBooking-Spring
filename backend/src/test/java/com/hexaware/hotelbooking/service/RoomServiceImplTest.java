package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.RoomDto;
import com.hexaware.hotelbooking.entities.Hotel;
import com.hexaware.hotelbooking.entities.Room;
import com.hexaware.hotelbooking.repository.HotelRepository;
import com.hexaware.hotelbooking.repository.RoomRepository;
import com.hexaware.hotelbooking.service.RoomServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private RoomDto roomDto;
    private Hotel hotel;
    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setName("Test Hotel");

        roomDto = new RoomDto();
        roomDto.setRoomId(1L);
        roomDto.setHotelId(1L);
        roomDto.setRoomType("Single");
        roomDto.setBaseFare(1000.0);
        roomDto.setMaxOccupancy(2);
        roomDto.setBedType("Single");
        roomDto.setSize("25 m²");

        room = new Room();
        room.setRoomId(1L);
        room.setHotel(hotel);
        room.setRoomType("Single");
        room.setBaseFare(1000.0);
        room.setMaxOccupancy(2);
        room.setBedType("single");
        room.setSize("25 m²");
    }

    @Test
    void testAddRoom_Success() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room savedRoom = roomService.addRoom(roomDto);

        assertNotNull(savedRoom);
        assertEquals(roomDto.getRoomType(), savedRoom.getRoomType());
        assertEquals("single", savedRoom.getBedType()); // normalized to lowercase
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testAddRoom_HotelNotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> roomService.addRoom(roomDto));
        assertEquals("Hotel not found", exception.getMessage());
    }

    @Test
    void testUpdateRoom_Success() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room updatedRoom = roomService.updateRoom(roomDto);

        assertNotNull(updatedRoom);
        assertEquals(roomDto.getBedType().toLowerCase(), updatedRoom.getBedType());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testGetRoomById_Success() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Room found = roomService.getRoomById(1L);
        assertNotNull(found);
        assertEquals(room.getRoomId(), found.getRoomId());
    }

    @Test
    void testGetRoomById_NotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.getRoomById(1L));
        assertEquals("Room not found", ex.getMessage());
    }

    @Test
    void testGetRoomsByHotelId() {
        List<Room> rooms = List.of(room);
        when(roomRepository.findRoomsByHotelId(1L)).thenReturn(rooms);

        List<Room> result = roomService.getRoomsByHotelId(1L);
        assertEquals(1, result.size());
        assertEquals(room.getRoomId(), result.get(0).getRoomId());
    }

    @Test
    void testDeleteRoom_Success() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).delete(room);

        roomService.deleteRoom(1L);

        verify(roomRepository, times(1)).delete(room);
    }

    @Test
    void testDeleteRoom_NotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomService.deleteRoom(1L));
        assertEquals("Room not found", ex.getMessage());
    }
}
