package com.openclassrooms.rental.mapper;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.openclassrooms.rental.entity.Rental;
import com.openclassrooms.rental.entity.DTO.RentalDTO;

// Mapper class for Rental entity and RentalDTO
public class RentalMapper {


    public static RentalDTO toDto(Rental rental) {
        if (rental == null) return null;
        return new RentalDTO(
            rental.getId(),
            rental.getName(),
            rental.getSurface(),
            rental.getPrice(),
            buildImageUrl(rental.getPicture()),
            rental.getDescription(),
            rental.getOwnerId() != null ? rental.getOwnerId() : null,
            rental.getCreatedAt(),
            rental.getUpdatedAt()
        );
    }

    public static String buildImageUrl(String fileName) {
        if (fileName == null) return null;

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/")
                .path(fileName)
                .toUriString();
    }

}
