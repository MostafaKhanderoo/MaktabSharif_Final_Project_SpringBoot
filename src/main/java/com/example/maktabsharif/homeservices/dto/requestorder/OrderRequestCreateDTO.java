package com.example.maktabsharif.homeservices.dto.requestorder;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;

public record OrderRequestCreateDTO(
        User SpecialistAcceptRequest,
        Orders order,
        Double SpecialistSuggestion
) {
}
