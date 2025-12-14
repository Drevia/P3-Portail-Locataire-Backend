package com.openclassrooms.rental.mapper;

import com.openclassrooms.rental.entity.DTO.RentalUserDTO;
import com.openclassrooms.rental.entity.RentalUser;

// Mapper class for RentalUser entity and RentalUserDTO
public class RentalUserMapper {


	public static RentalUserDTO toDto(RentalUser user) {
		if (user == null) return null;
		return new RentalUserDTO(
            user.getId(),
			user.getEmail(),
			user.getName(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
	}

}
