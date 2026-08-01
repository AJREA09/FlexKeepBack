package com.puce.backendProyectoFinal.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.puce.backendProyectoFinal.model.SavingGoal;
import com.puce.backendProyectoFinal.service.SavingGoalService;

@RestController
@RequestMapping("/api/saving-goals")
public class SavingGoalController {
    @Autowired
    private SavingGoalService savingGoalService;


    @PostMapping //Create the goal of the user
    public ResponseEntity<SavingGoal> createSavingGoal(@RequestBody SavingGoalRequest request) {
        try {
            SavingGoal createdGoal = savingGoalService.createSavingGoal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PatchMapping("/{id}/add-amount") //Increment the amount of the goal
    public ResponseEntity<SavingGoal> addAmountToGoal(@PathVariable Long id, @RequestParam BigDecimal amount) {
        try {
            SavingGoal updatedGoal = savingGoalService.updateSavedAmount(id, amount);
            return ResponseEntity.ok(updatedGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{username}") //Obtain goal of the user
    public ResponseEntity<SavingGoal> getSavingGoal(@PathVariable String username) {
        Optional<SavingGoal> optionalSavingGoal = savingGoalService.getSavingGoalByUsername(username);
        if (optionalSavingGoal.isPresent()) {
            return ResponseEntity.ok(optionalSavingGoal.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/{id}") //Delete goal 
    public ResponseEntity<Void> deleteSavingGoal(@PathVariable Long id) {
        try {
            savingGoalService.deleteSavingGoal(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public static class SavingGoalRequest {
        private String username;
        private BigDecimal targetAmount;
        private LocalDate deadline;
        private BigDecimal savedAmount;
        private Boolean status;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public BigDecimal getTargetAmount() {
            return targetAmount;
        }

        public void setTargetAmount(BigDecimal targetAmount) {
            this.targetAmount = targetAmount;
        }

        public LocalDate getDeadline() {
            return deadline;
        }

        public void setDeadline(LocalDate deadline) {
            this.deadline = deadline;
        }

        public BigDecimal getSavedAmount() {
            return savedAmount;
        }

        public void setSavedAmount(BigDecimal savedAmount) {
            this.savedAmount = savedAmount;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }
}