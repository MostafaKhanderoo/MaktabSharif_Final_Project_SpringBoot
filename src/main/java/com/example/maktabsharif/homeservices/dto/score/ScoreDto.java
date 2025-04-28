package com.example.maktabsharif.homeservices.dto.score;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScoreDto {
    private Long id;
    private User specialistId;
    @Size(max = 5)
    @PositiveOrZero
    private int specialistScore;
    private String description;
    private Orders orderId;
}
