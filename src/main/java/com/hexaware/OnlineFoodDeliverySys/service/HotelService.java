package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;

public interface HotelService {
    Hotel addHotel(HotelDto dto);
    Hotel updateHotel(Hotel hotel);
    Hotel getByHotelId(Long hotelId);
    String deleteByHotelId(Long hotelId);
    List<Hotel> getAllHotels();
    List<Hotel> searchByLocation(String location);
}
