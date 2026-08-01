package com.puce.backendProyectoFinal.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puce.backendProyectoFinal.model.User;
import com.puce.backendProyectoFinal.model.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordService passwordService;
    
    public User registerUser(User user) {
        String password = user.getPassword();
        
        validateUser(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(password);
        validateName(user.getName());
        validateLastname(user.getLastname());

        user.setPassword(passwordService.encodePassword(password));
        return userRepository.save(user);
    }

    private void validateUser(String username) {
        String regex = "^[A-Za-záéíóúÁÉÍÓÚÑñ0-9\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        
        if (!matcher.matches()) {
            throw new RuntimeException("El nombre de usuario no es válido. Solo se permiten letras, números y espacios.");
        }
    }
    
    private void validateEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        
        if (!matcher.matches()) {
            throw new RuntimeException("La dirección de correo electrónico no es válida.");
        }
    }
    
    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres.");
        }
        
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?!.*\\s).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        
        if (!matcher.matches()) {
            throw new RuntimeException("La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y no debe tener espacios.");
        }
    }
    
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío.");
        }

        String regex = "^[A-Za-záéíóúÁÉÍÓÚÑñ\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()) {
            throw new RuntimeException("El nombre solo puede contener letras y espacios.");
        }
    }

    private void validateLastname(String lastname) {
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new RuntimeException("El apellido no puede estar vacío.");
        }

        String regex = "^[A-Za-záéíóúÁÉÍÓÚÑñ\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(lastname);

        if (!matcher.matches()) {
            throw new RuntimeException("El apellido solo puede contener letras y espacios.");
        }
    }
    
    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isEmpty()) {
            throw new RuntimeException("No se encontró el usuario " + username);
        }
        
        String storedPassword = user.get().getPassword();
        if (!passwordService.matches(password, storedPassword)) {
            throw new RuntimeException("La contraseña ingresada es incorrecta.");
        }

        return user.get();
    }
}
