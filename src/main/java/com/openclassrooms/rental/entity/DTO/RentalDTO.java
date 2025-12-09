package com.openclassrooms.rental.entity.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
public class RentalDTO {
    private Long id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    @JsonProperty("owner_id")
    private Long ownerId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
