package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel addHotel(HotelDto dto);
    Hotel updateHotel(Hotel hotel);
    Hotel getByHotelId(Long hotelId);
    String deleteByHotelId(Long hotelId);
    List<Hotel> getAllHotels();
    List<Hotel> searchHotelsByLocation(String location);
}
