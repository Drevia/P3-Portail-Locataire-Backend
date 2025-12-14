package com.openclassrooms.rental.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.rental.entity.JwtResponse;
import com.openclassrooms.rental.entity.LoginRequest;
import com.openclassrooms.rental.entity.RegisterRequest;
import com.openclassrooms.rental.entity.RentalUser;
import com.openclassrooms.rental.mapper.RentalUserMapper;
import com.openclassrooms.rental.service.JWTService;

import com.openclassrooms.rental.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class LoginController {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    /**
     * <p>Authenticate a user and generate a JWT token.</p>
     * @param entity The login request containing email and password.
     * @return A ResponseEntity containing the JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest entity) {
        
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(entity.email(), entity.password())
        );

        String jwt = jwtService.generateToken(entity.email());
        

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /**
     * <p>Register a new user.</p>
     * @param request The registration request containing user details.
     * @return A ResponseEntity containing the JWT token.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Check if the email already exists
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

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

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /**
     * <p>Get information about the currently authenticated user.</p>
     * @param principal The security principal representing the authenticated user.
     * @return A ResponseEntity containing the user information.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        // Récupérer l'utilisateur depuis le contexte d'authentification
        Authentication authentication = (org.springframework.security.core.Authentication) principal;
        Object principalObj = authentication.getPrincipal();
        if (principalObj instanceof RentalUser) {
            RentalUser user = (RentalUser) principalObj;
            return ResponseEntity.ok(RentalUserMapper.toDto(user));
        }
        return ResponseEntity.ok(new JwtResponse(principal.getName()));
    }
    
    
    
}
