package com.example.maktabsharif.homeservices.dto.score;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ScoreUpdateDTO(
        Long id,
        User specialistId,
        @Size(max = 5)
        @PositiveOrZero
        int specialistScore,
        String description,
        Orders orderId
) {
}
