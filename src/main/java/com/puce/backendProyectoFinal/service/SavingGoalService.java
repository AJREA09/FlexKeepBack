package com.puce.backendProyectoFinal.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puce.backendProyectoFinal.controller.SavingGoalController;
import com.puce.backendProyectoFinal.model.SavingGoal;
import com.puce.backendProyectoFinal.model.User;
import com.puce.backendProyectoFinal.model.repository.SavingGoalRepository;
import com.puce.backendProyectoFinal.model.repository.UserRepository;

@Service
public class SavingGoalService {
    @Autowired
    private SavingGoalRepository savingGoalRepository;

    @Autowired
    private UserRepository userRepository;


    public SavingGoal createSavingGoal(SavingGoalController.SavingGoalRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SavingGoal savingGoal = new SavingGoal(
            user,
            request.getTargetAmount(),
            request.getDeadline(),
            request.getSavedAmount(),
            request.getStatus()
        );

        return savingGoalRepository.save(savingGoal);
    }

    public SavingGoal updateSavedAmount(Long id, BigDecimal amountToAdd) {
        Optional<SavingGoal> optionalSavingGoal = savingGoalRepository.findById(id);
        if (optionalSavingGoal.isPresent()) {
            SavingGoal savingGoal = optionalSavingGoal.get();
            BigDecimal newSavedAmount = savingGoal.getSavedAmount().add(amountToAdd);
            savingGoal.setSavedAmount(newSavedAmount);

            if (newSavedAmount.compareTo(savingGoal.getTargetAmount()) >= 0) {
                savingGoal.setStatus(true);
            }
            return savingGoalRepository.save(savingGoal);
        } else {
            throw new RuntimeException("Saving goal not found with id: " + id);
        }
    }
    
    public Optional<SavingGoal> getSavingGoalByUsername(String username) {
        return savingGoalRepository.findByUsername(username);
    }
    
    public void deleteSavingGoal(Long id) {
        Optional<SavingGoal> optionalSavingGoal = savingGoalRepository.findById(id);
        if (optionalSavingGoal.isPresent()) {
            savingGoalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Saving goal not found with id: " + id);
        }
    }
}
