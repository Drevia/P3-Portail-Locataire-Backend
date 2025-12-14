package com.openclassrooms.rental.service;

import com.openclassrooms.rental.entity.RentalMessage;
import com.openclassrooms.rental.entity.DTO.RentalMessageCreateDTO;
import com.openclassrooms.rental.repository.RentalMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalMessageService {
    private final RentalMessageRepository rentalMessageRepository;

    public RentalMessage createMessage(Long userId, RentalMessageCreateDTO dto) {
        RentalMessage message = new RentalMessage();
        message.setMessage(dto.getMessage());
        message.setUserId(userId);
        message.setRentalId(dto.getRentalId());
        message.setCreatedAt(java.time.LocalDateTime.now());
        return rentalMessageRepository.save(message);
    }
}
