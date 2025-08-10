package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;

public interface TransportationService {
    Transportation addTransportation(TransportationDto dto);
    Transportation updateTransportation(Transportation transportation);
    Transportation getByTransportationId(Long transportId);
    String deleteByTransportationId(Long transportId);
    List<Transportation> getAllTransportations();
    List<Transportation> getByHotelId(Long hotelId);
}
