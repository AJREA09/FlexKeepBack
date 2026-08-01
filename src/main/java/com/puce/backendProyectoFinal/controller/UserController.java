package com.puce.backendProyectoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puce.backendProyectoFinal.model.LoginRequest;
import com.puce.backendProyectoFinal.model.User;
import com.puce.backendProyectoFinal.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")  //Register user
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User userRegistered = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userRegistered);
        } catch (RuntimeException e) {
        	return ResponseEntity.badRequest().body(e.getMessage());
        	
        }
    }

    @PostMapping("/login") //Login user
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest loginRequest) {
        try {
            User authenticatedUser = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}