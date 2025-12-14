package com.openclassrooms.rental.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.rental.service.RentalService;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    
    /**
     * <p>Get a rental by its ID.</p>
     * @param id The ID of the rental to retrieve.
     * @return A ResponseEntity containing the RentalDTO if found, or null if not found
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable String id) {
        
        return ResponseEntity.ok(rentalService.getRentalById(Long.parseLong(id)));
    }


    /**
     * <p>Get all rentals.</p>
     * @return A ResponseEntity containing a map with a list of RentalDTOs.
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rentals found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @GetMapping()
    public ResponseEntity<Map<String, List<RentalDTO>>> getAllRentals() {
        log.info("Fetching all rentals");
        List<RentalDTO> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(Map.of("rentals", rentals));
    }

    /**
     * <p>Create a new rental.</p>
     * @param name The name of the rental.
     * @param surface The surface area of the rental.
     * @param price The price of the rental.
     * @param description The description of the rental.
     * @param picture The picture file of the rental.
     * @return A ResponseEntity containing the created RentalDTO.
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental created"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
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

    /**
     * <p>Update an existing rental.</p>
     * @param id The ID of the rental to update.
     * @param name The new name of the rental.
     * @param surface The new surface area of the rental.
     * @param price The new price of the rental.
     * @param description The new description of the rental.
     * @return A ResponseEntity containing the updated RentalDTO.
     */
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental updated"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
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
