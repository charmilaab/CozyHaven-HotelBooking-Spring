package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.HotelDto;
import com.hexaware.hotelbooking.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel addHotel(HotelDto dto);
    Hotel updateHotel(Hotel hotel);
    String deleteHotel(Long hotelId);
    Hotel getByHotelId(Long hotelId);
    List<Hotel> getAllHotels();
    List<Hotel> searchHotelsByLocation(String location);
}
