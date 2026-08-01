package com.puce.backendProyectoFinal.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.puce.backendProyectoFinal.model.SavingGoal;

@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {
    @Query("SELECT sg FROM SavingGoal sg WHERE sg.user.username = :username")
    Optional<SavingGoal> findByUsername(@Param("username") String username);
}
