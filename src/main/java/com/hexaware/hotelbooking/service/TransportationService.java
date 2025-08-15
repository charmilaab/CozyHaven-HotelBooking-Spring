package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.TransportationDto;
import com.hexaware.hotelbooking.entities.Transportation;

import java.util.List;

public interface TransportationService {
    Transportation addTransport(TransportationDto dto);
    Transportation updateTransport(Transportation transport);
    Transportation getByTransportId(Long transportId);
    String deleteByTransportId(Long transportId);
    List<Transportation> getAllTransport();
    List<Transportation> getTransportByHotel(Long hotelId);
}
