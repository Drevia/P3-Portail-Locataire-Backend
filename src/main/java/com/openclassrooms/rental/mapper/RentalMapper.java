package com.openclassrooms.rental.mapper;

import com.openclassrooms.rental.entity.Rental;
import com.openclassrooms.rental.entity.DTO.RentalDTO;

public class RentalMapper {
    public static RentalDTO toDto(Rental rental) {
        if (rental == null) return null;
        return new RentalDTO(
            rental.getId(),
            rental.getName(),
            rental.getSurface(),
            rental.getPrice(),
            rental.getPicture(),
            rental.getDescription(),
            rental.getOwnerId() != null ? rental.getOwnerId() : null,
            rental.getCreatedAt(),
            rental.getUpdatedAt()
        );
    }

}
