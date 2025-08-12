package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import com.hexaware.OnlineFoodDeliverySys.repository.HotelRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.TransportationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportationServiceImpl implements TransportationService {

    @Autowired
    private TransportationRepository repo;

    @Autowired
    private HotelRepository hotelRepo;

    @Override
    public Transportation addTransport(TransportationDto dto) {
        Transportation transport = new Transportation();
        transport.setTransportId(dto.getTransportId());
        transport.setType(dto.getType());
        transport.setDetails(dto.getDetails());
        transport.setCost(dto.getCost());
        Hotel hotel = hotelRepo.findById(dto.getHotelId()).orElse(null);
        transport.setHotel(hotel);
        return repo.save(transport);
    }

    @Override
    public Transportation updateTransport(Transportation transport) {
        return repo.save(transport);
    }

    @Override
    public Transportation getByTransportId(Long transportId) {
        return repo.findById(transportId).orElse(null);
    }

    @Override
    public String deleteByTransportId(Long transportId) {
        repo.deleteById(transportId);
        return "Transportation deleted successfully";
    }

    @Override
    public List<Transportation> getAllTransport() {
        return repo.findAll();
    }

    @Override
    public List<Transportation> getTransportByHotel(Long hotelId) {
        return repo.findTransportOptionsByHotel(hotelId);
    }
}
