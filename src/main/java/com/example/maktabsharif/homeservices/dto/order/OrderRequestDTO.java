package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderRequestDTO {
    private User SpecialistAcceptRequest;


    private Orders order;


    private Double  SpecialistSuggestion;
}
