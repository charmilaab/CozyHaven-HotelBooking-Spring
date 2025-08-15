package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.TransportationDto;
import com.hexaware.hotelbooking.entities.Hotel;
import com.hexaware.hotelbooking.entities.Transportation;
import com.hexaware.hotelbooking.exceptions.HotelNotFoundException;
import com.hexaware.hotelbooking.exceptions.TransportationNotFoundException;
import com.hexaware.hotelbooking.repository.HotelRepository;
import com.hexaware.hotelbooking.repository.TransportationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransportationServiceImpl implements TransportationService {

    @Autowired private TransportationRepository repo;
    @Autowired private HotelRepository hotelRepo;

    @Override
    public Transportation addTransport(TransportationDto dto) {
        Hotel hotel = hotelRepo.findById(dto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found: " + dto.getHotelId()));
        Transportation t = new Transportation();
        t.setTransportId(dto.getTransportId());
        t.setHotel(hotel);
        t.setType(dto.getType());
        t.setDetails(dto.getDetails());
        t.setCost(dto.getCost());
        return repo.save(t);
    }

    @Override
    public Transportation updateTransport(Transportation transport) {
        repo.findById(transport.getTransportId())
            .orElseThrow(() -> new TransportationNotFoundException("Transport not found: " + transport.getTransportId()));
        return repo.save(transport);
    }

    @Override
    public Transportation getByTransportId(Long transportId) {
        return repo.findById(transportId)
                .orElseThrow(() -> new TransportationNotFoundException("Transport not found: " + transportId));
    }

    @Override
    public String deleteByTransportId(Long transportId) {
        Transportation t = getByTransportId(transportId);
        repo.delete(t);
        return "Transportation deleted successfully";
    }

    @Override
    public List<Transportation> getAllTransport() { return repo.findAll(); }

    @Override
    public List<Transportation> getTransportByHotel(Long hotelId) {
        return repo.findTransportOptionsByHotel(hotelId);
    }
}
