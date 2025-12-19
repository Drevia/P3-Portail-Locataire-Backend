package com.openclassrooms.rental.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.rental.entity.DTO.RentalUserDTO;
import com.openclassrooms.rental.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<RentalUserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id) != null ?
            ResponseEntity.ok(userService.getUserById(id)) :
            ResponseEntity.notFound().build();
    }
    
}
