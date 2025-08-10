package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Hotel;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import com.hexaware.OnlineFoodDeliverySys.exceptions.TransportationNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.TransportationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransportationServiceImpl implements TransportationService {

    @Autowired
    private TransportationRepository transportationRepo;

    @Override
    public Transportation addTransportation(TransportationDto dto) {
        Transportation transportation = new Transportation();

        // map simple fields
        transportation.setTransportId(dto.getTransportId());
        transportation.setType(dto.getType());
        transportation.setDetails(dto.getDetails());
        transportation.setCost(dto.getCost());

        // map nested HotelDto -> Hotel entity (if provided)
        if (dto.getHotel() != null) {
            Hotel hotel = new Hotel();
            if (dto.getHotel().getHotelId() != null) {
                hotel.setHotelId(dto.getHotel().getHotelId());
            }
            // optionally map more hotel fields here if needed
            transportation.setHotel(hotel);
        }

        log.info("Adding new transportation: {}", dto);
        return transportationRepo.save(transportation);
    }

    @Override
    public Transportation updateTransportation(Transportation transportation) {
        if (!transportationRepo.existsById(transportation.getTransportId())) {
            throw new TransportationNotFoundException(
                "Transportation not found with ID: " + transportation.getTransportId()
            );
        }
        log.info("Updating transportation with ID: {}", transportation.getTransportId());
        return transportationRepo.save(transportation);
    }

    @Override
    public Transportation getByTransportationId(Long transportId) {
        return transportationRepo.findById(transportId)
                .orElseThrow(() -> new TransportationNotFoundException(
                    "Transportation not found with ID: " + transportId
                ));
    }

    @Override
    public String deleteByTransportationId(Long transportId) {
        if (!transportationRepo.existsById(transportId)) {
            throw new TransportationNotFoundException(
                "Cannot delete. Transportation not found with ID: " + transportId
            );
        }
        transportationRepo.deleteById(transportId);
        return "Transportation deleted successfully";
    }

    @Override
    public List<Transportation> getAllTransportations() {
        return transportationRepo.findAll();
    }

    @Override
    public List<Transportation> getByHotelId(Long hotelId) {
                return transportationRepo.findTransportOptionsByHotel(hotelId);
    }
}
