package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.LoyaltyProgramDto;
import com.hexaware.OnlineFoodDeliverySys.entities.LoyaltyProgram;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.LoyaltyProgramNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.LoyaltyProgramRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

    @Autowired
    private LoyaltyProgramRepository loyaltyRepo;

    @Override
    public LoyaltyProgram addLoyaltyProgram(LoyaltyProgramDto dto) {
        LoyaltyProgram loyalty = new LoyaltyProgram();
        loyalty.setLoyaltyId(dto.getLoyaltyId());

        // Convert UserDto → User entity
        User user = new User();
        user.setUserId(dto.getUser().getUserId());
        loyalty.setUser(user);

        loyalty.setPoints(dto.getPoints());
        loyalty.setTier(dto.getTier());

        log.info("Adding new loyalty program: {}", dto);
        return loyaltyRepo.save(loyalty);
    }

    @Override
    public LoyaltyProgram updateLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        if (!loyaltyRepo.existsById(loyaltyProgram.getLoyaltyId())) {
            throw new LoyaltyProgramNotFoundException(
                "Loyalty program not found with ID: " + loyaltyProgram.getLoyaltyId()
            );
        }
        return loyaltyRepo.save(loyaltyProgram);
    }

    @Override
    public LoyaltyProgram getByLoyaltyId(Long loyaltyId) {
        return loyaltyRepo.findById(loyaltyId)
                .orElseThrow(() -> new LoyaltyProgramNotFoundException(
                    "Loyalty program not found with ID: " + loyaltyId
                ));
    }

    @Override
    public String deleteByLoyaltyId(Long loyaltyId) {
        if (!loyaltyRepo.existsById(loyaltyId)) {
            throw new LoyaltyProgramNotFoundException(
                "Cannot delete. Loyalty program not found with ID: " + loyaltyId
            );
        }
        loyaltyRepo.deleteById(loyaltyId);
        return "Loyalty program deleted successfully";
    }

    @Override
    public List<LoyaltyProgram> getAllLoyaltyPrograms() {
        return loyaltyRepo.findAll();
    }

    @Override
    public Optional<LoyaltyProgram> getByUserId(Long userId) {
        return loyaltyRepo.findByUserId(userId);
    }
}
