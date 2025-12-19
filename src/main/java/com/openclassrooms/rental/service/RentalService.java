package com.openclassrooms.rental.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.rental.entity.DTO.RentalDTO;
import com.openclassrooms.rental.mapper.RentalMapper;
import com.openclassrooms.rental.repository.RentalRepository;
import com.openclassrooms.rental.entity.Rental;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalService {

    public final RentalRepository rentalRepository;

    public RentalDTO getRentalById(Long id) {
        return RentalMapper.toDto(rentalRepository.findById(id).orElse(null));
    }

    public List<RentalDTO> getAllRentals() {
        List<RentalDTO> rentals = rentalRepository.findAll().stream()
                .map(RentalMapper::toDto)
                .toList();


        return rentals;
    }

    public RentalDTO createRental(String name, Double surface, Double price, String description, MultipartFile image, Long ownerId) {
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setOwnerId(ownerId);
        rental.setCreatedAt(java.time.LocalDateTime.now());
        rental.setUpdatedAt(java.time.LocalDateTime.now());
        try {
            String originalFilename = image.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path uploadPath = Paths.get("src/main/resources/static/images/" + fileName);
            Files.write(uploadPath, image.getBytes());
            rental.setPicture(fileName);
        } catch (Exception e) {
            rental.setPicture(null);
        }
        Rental saved = rentalRepository.save(rental);
        return RentalMapper.toDto(saved);
    }

    public RentalDTO updateRental(Long id, String name, Double surface, Double price, String description, Long ownerId) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        if (!rental.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized to update this rental");
        }
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setUpdatedAt(LocalDateTime.now());
        Rental updated = rentalRepository.save(rental);
        return RentalMapper.toDto(updated);
    }
}
