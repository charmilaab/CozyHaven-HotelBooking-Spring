package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import java.util.List;

public interface HotelService {
    Hotel createHotel(Hotel hotel);
    Hotel getHotelById(Long hotelId);
    List<Hotel> getAllHotels();
    Hotel updateHotel(Long hotelId, Hotel hotel);
    void deleteHotel(Long hotelId);
}
