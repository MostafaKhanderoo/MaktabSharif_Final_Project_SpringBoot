package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.SubService;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ServiceCreateDTO(
        @NotEmpty
        String name,
        @NotEmpty
        List<SubService> subServices
) {
}
