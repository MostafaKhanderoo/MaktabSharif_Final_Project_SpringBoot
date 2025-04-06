package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import jakarta.validation.constraints.NotNull;

public record SubServiceUpdateDTO(
        @NotNull(message = "field id can not be null for update operations")
        Long id,
        String name,
        Double basePrice,
        String description,
        ServiceEntity service
) {
}
