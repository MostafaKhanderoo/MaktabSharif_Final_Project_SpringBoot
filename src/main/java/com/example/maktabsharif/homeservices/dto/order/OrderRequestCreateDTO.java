package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;

public record OrderRequestCreateDTO(
        Long SpecialistAcceptRequest,
        Long order,
        Double  SpecialistSuggestion
) {
}
