package com.openclassrooms.rental.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.rental.service.RentalService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.openclassrooms.rental.entity.RentalUser;
import com.openclassrooms.rental.entity.DTO.RentalDTO;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
@Log4j2
public class RentalController {

    private final RentalService rentalService;
    
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable String id) {
        
        return ResponseEntity.ok(rentalService.getRentalById(Long.parseLong(id)));
    }

    @GetMapping()
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        log.info("Fetching all rentals");
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<RentalDTO> createRentals(
        @RequestParam("name") String name,
        @RequestParam("surface") Double surface,
        @RequestParam("price") Double price,
        @RequestParam("description") String description,
        @RequestParam("picture") MultipartFile picture
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RentalUser user = (RentalUser) authentication.getPrincipal();
            Long ownerId = user.getId();
            return ResponseEntity.ok(rentalService.createRental(name, surface, price, description, picture, ownerId));
        } catch (Exception e) {
            log.error("Erreur lors de la création de la location : " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/id")
    public ResponseEntity<RentalDTO> updateRentals(
        @PathVariable String id,
        @RequestParam("name") String name,
        @RequestParam("surface") Double surface,
        @RequestParam("price") Double price,
        @RequestParam("description") String description) 
    {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RentalUser user = (RentalUser) authentication.getPrincipal();
            Long ownerId = user.getId();
            return ResponseEntity.ok(rentalService.updateRental(Long.parseLong(id), name, surface, price, description, ownerId));
        } catch (Exception e) {
            log.error("Erreur lors de la mise a jour de la location : " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
