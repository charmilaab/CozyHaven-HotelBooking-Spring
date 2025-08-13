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
    @Autowired private HotelRepository repo;

    @Override
    public Hotel addHotel(HotelDto dto) {
        Hotel h = new Hotel();
        h.setHotelId(dto.getHotelId());
        h.setName(dto.getName());
        h.setLocation(dto.getLocation());
        h.setDescription(dto.getDescription());
        h.setAmenities(dto.getAmenities());
        return repo.save(h);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        repo.findById(hotel.getHotelId())
            .orElseThrow(() -> new HotelNotFoundException("Hotel not found: " + hotel.getHotelId()));
        return repo.save(hotel);
    }

    @Override
    public Hotel getByHotelId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found: " + id));
    }

    @Override
    public String deleteByHotelId(Long id) {
        Hotel h = getByHotelId(id);
        repo.delete(h);
        return "Hotel deleted successfully";
    }

    @Override
    public List<Hotel> getAllHotels() { return repo.findAll(); }

    @Override
    public List<Hotel> searchByLocation(String location) {
        return repo.searchHotelsByLocation(location);
    }
}
