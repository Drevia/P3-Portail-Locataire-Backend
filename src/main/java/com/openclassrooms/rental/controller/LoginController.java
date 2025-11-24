package com.openclassrooms.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.rental.entity.JwtResponse;
import com.openclassrooms.rental.entity.LoginRequest;
import com.openclassrooms.rental.entity.RegisterRequest;
import com.openclassrooms.rental.entity.User;
import com.openclassrooms.rental.service.JWTService;

import com.openclassrooms.rental.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class LoginController {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest entity) {
        
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(entity.email(), entity.password())
        );

        String jwt = jwtService.generateToken(entity.email()); //appeler le service
        

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }

        // Encoder le mot de passe
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(request.password());

        // Créer et sauvegarder l'utilisateur
        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur créé avec succès");
    }
    
    
}
