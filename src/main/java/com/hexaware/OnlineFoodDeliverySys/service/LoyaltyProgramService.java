package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import java.util.Optional;
import com.hexaware.OnlineFoodDeliverySys.dto.LoyaltyProgramDto;
import com.hexaware.OnlineFoodDeliverySys.entities.LoyaltyProgram;

public interface LoyaltyProgramService {
    LoyaltyProgram addLoyaltyProgram(LoyaltyProgramDto dto);
    LoyaltyProgram updateLoyaltyProgram(LoyaltyProgram loyaltyProgram);
    LoyaltyProgram getByLoyaltyId(Long loyaltyId);
    String deleteByLoyaltyId(Long loyaltyId);
    List<LoyaltyProgram> getAllLoyaltyPrograms();
    Optional<LoyaltyProgram> getByUserId(Long userId);
}
