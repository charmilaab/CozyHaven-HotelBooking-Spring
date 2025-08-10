package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.entities.Transportation;
import java.util.List;

public interface TransportationService {
    Transportation createTransportation(Transportation transportation);
    Transportation getTransportationById(Long transportationId);
    List<Transportation> getAllTransportations();
    Transportation updateTransportation(Long transportationId, Transportation transportation);
    void deleteTransportation(Long transportationId);
}
