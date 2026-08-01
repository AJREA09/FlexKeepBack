package com.puce.backendProyectoFinal.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puce.backendProyectoFinal.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}