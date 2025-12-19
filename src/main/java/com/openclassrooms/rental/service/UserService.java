package com.openclassrooms.rental.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.rental.entity.RentalUser;
import com.openclassrooms.rental.entity.DTO.RentalUserDTO;
import com.openclassrooms.rental.mapper.RentalUserMapper;
import com.openclassrooms.rental.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public RentalUserDTO getUserById(Long id) {
        RentalUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return RentalUserMapper.toDto(user);
    }
}
