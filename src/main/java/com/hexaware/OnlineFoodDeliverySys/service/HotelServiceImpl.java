package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.HotelDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.exceptions.HotelNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepo;

    @Override
    public Hotel addHotel(HotelDto dto) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(dto.getHotelId());
        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setDescription(dto.getDescription());
        hotel.setAmenities(dto.getAmenities());

        log.info("Adding new hotel: {}", dto);
        return hotelRepo.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        if (!hotelRepo.existsById(hotel.getHotelId())) {
            throw new HotelNotFoundException("Hotel not found with ID: " + hotel.getHotelId());
        }
        return hotelRepo.save(hotel);
    }

    @Override
    public Hotel getByHotelId(Long hotelId) {
        return hotelRepo.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with ID: " + hotelId));
    }

    @Override
    public String deleteByHotelId(Long hotelId) {
        if (!hotelRepo.existsById(hotelId)) {
            throw new HotelNotFoundException("Cannot delete. Hotel not found with ID: " + hotelId);
        }
        hotelRepo.deleteById(hotelId);
        return "Hotel deleted successfully";
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public List<Hotel> searchByLocation(String location) {
        return hotelRepo.searchHotelsByLocation(location);
    }
}
