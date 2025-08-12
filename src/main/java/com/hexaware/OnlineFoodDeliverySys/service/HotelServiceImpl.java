package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository repo;

    @Override
    public Hotel addHotel(HotelDto dto) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(dto.getHotelId());
        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setDescription(dto.getDescription());
        hotel.setAmenities(dto.getAmenities());
        return repo.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        return repo.save(hotel);
    }

    @Override
    public Hotel getByHotelId(Long hotelId) {
        return repo.findById(hotelId).orElse(null);
    }

    @Override
    public String deleteByHotelId(Long hotelId) {
        repo.deleteById(hotelId);
        return "Hotel deleted successfully";
    }

    @Override
    public List<Hotel> getAllHotels() {
        return repo.findAll();
    }

    @Override
    public List<Hotel> searchHotelsByLocation(String location) {
        return repo.searchHotelsByLocation(location);
    }
}
