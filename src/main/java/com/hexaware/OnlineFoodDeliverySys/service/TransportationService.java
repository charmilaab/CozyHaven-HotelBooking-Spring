package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.TransportationDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import java.util.List;

public interface TransportationService {
    Transportation addTransport(TransportationDto dto);
    Transportation updateTransport(Transportation transport);
    Transportation getByTransportId(Long transportId);
    String deleteByTransportId(Long transportId);
    List<Transportation> getAllTransport();
    List<Transportation> getTransportByHotel(Long hotelId);
}
