package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;

public record SubServiceCreateDTO(
        String name,
        Double basePrice,
        String description,
        ServiceEntity service
) {
}
