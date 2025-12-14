package com.openclassrooms.rental.controller;

import com.openclassrooms.rental.entity.DTO.RentalMessageCreateDTO;
import com.openclassrooms.rental.entity.RentalUser;
import com.openclassrooms.rental.service.RentalMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class RentalMessageController {

    private final RentalMessageService rentalMessageService;

    /**
     * <p>Create a new rental message.</p>
     * @param dto The DTO containing message details.
     * @return A ResponseEntity containing the created RentalMessage.
     */
    @PostMapping()
    public ResponseEntity<?> createMessage(@RequestBody RentalMessageCreateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RentalUser user = (RentalUser) authentication.getPrincipal();
        return ResponseEntity.ok(rentalMessageService.createMessage(user.getId(), dto));
    }
}