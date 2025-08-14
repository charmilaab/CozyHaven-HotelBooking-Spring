package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.exceptions.HotelNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(HotelDto dto) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(dto.getHotelId());
        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setDescription(dto.getDescription());
        hotel.setAmenities(dto.getAmenities());
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(hotel.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException(
                        "Hotel not found with ID: " + hotel.getHotelId()));
        existingHotel.setName(hotel.getName());
        existingHotel.setLocation(hotel.getLocation());
        existingHotel.setDescription(hotel.getDescription());
        existingHotel.setAmenities(hotel.getAmenities());
        return hotelRepository.save(existingHotel);
    }

    @Override
    public String deleteHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(
                        "Hotel not found with ID: " + hotelId));
        hotelRepository.delete(hotel);
        return "Hotel deleted successfully";
    }

    @Override
    public Hotel getByHotelId(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(
                        "Hotel not found with ID: " + hotelId));
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> searchHotelsByLocation(String location) {
        return hotelRepository.searchHotelsByLocation(location);
    }
}
