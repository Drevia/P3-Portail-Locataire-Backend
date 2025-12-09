package com.openclassrooms.rental.entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalMessageCreateDTO {
    private String message;
    private Long rentalId;
}
