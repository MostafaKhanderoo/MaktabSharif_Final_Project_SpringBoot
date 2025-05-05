package com.example.maktabsharif.homeservices.dto.score;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ScoreCreateDTO(

        Long specialistId,
        @Size(max = 5)
        @PositiveOrZero
        @NotEmpty
        Integer specialistScore,
        String description,
        Long orderId
) {
}
