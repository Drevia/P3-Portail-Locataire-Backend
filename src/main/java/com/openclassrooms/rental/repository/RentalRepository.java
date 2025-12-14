package com.openclassrooms.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.rental.entity.Rental;
import java.util.List;


public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByOwnerId(Long ownerId);
}
