package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.SubService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ServiceUpdateDTO(
        @NotNull(message = "field id can not be null for update operations")
        Long id,
        @NotEmpty
        String name,
        @NotEmpty
        List<SubService> subServices
) {
}
