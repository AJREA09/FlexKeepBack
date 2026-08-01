package com.puce.backendProyectoFinal.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "saving_goals")
public class SavingGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_goal")
    private Long idGoal;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "saved_amount", nullable = false)
    private BigDecimal savedAmount;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public SavingGoal() {}

    public SavingGoal(User user, BigDecimal targetAmount, LocalDate deadline, BigDecimal savedAmount, Boolean status) {
        this.user = user;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.savedAmount = savedAmount;
        this.status = status;
    }

    public Long getIdGoal() {
        return idGoal;
    }

    public void setIdGoal(Long idGoal) {
        this.idGoal = idGoal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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