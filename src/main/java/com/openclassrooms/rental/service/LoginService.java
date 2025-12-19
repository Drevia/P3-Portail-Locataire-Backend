package com.openclassrooms.rental.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.rental.entity.LoginRequest;
import com.openclassrooms.rental.entity.RegisterRequest;
import com.openclassrooms.rental.entity.RentalUser;
import com.openclassrooms.rental.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {

    private final JWTService jwtService;

    private final UserRepository userRepository;

    public String login(LoginRequest entity){
        return jwtService.generateToken(entity.email());
    }

    public String createUser(RegisterRequest request) {
        

        // Encode the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(request.password());

        // Create and save the user
        RentalUser user = new RentalUser();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String jwt = jwtService.generateToken(request.email());
        return jwt;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
