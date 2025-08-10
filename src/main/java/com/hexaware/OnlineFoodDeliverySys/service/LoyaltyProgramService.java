package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.entities.LoyaltyProgram;
import java.util.List;

public interface LoyaltyProgramService {
    LoyaltyProgram createLoyaltyProgram(LoyaltyProgram program);
    LoyaltyProgram getLoyaltyProgramById(Long programId);
    List<LoyaltyProgram> getAllLoyaltyPrograms();
    LoyaltyProgram updateLoyaltyProgram(Long programId, LoyaltyProgram program);
    void deleteLoyaltyProgram(Long programId);
}
