package com.openclassrooms.rental.repository;

import com.openclassrooms.rental.entity.RentalMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalMessageRepository extends JpaRepository<RentalMessage, Long> {
}
