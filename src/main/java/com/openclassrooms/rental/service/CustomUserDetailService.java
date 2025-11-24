package com.openclassrooms.rental.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.rental.entity.User;
import com.openclassrooms.rental.repository.UserRepository;


import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        
        return org.springframework.security.core.userdetails.User.builder()
        .username(user.getName())
        .password(user.getPassword())
        .roles("USER")
        .build();
    }

}
