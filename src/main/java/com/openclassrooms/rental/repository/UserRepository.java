package com.openclassrooms.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.rental.entity.RentalUser;

public interface UserRepository extends JpaRepository<RentalUser, Long> {
    
    Optional<RentalUser> findByEmail(String email);
}
