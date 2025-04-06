package com.example.maktabsharif.homeservices.dto.requestorder;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;

public class OrderRequestDTO {

    private Long id;

    private User SpecialistAcceptRequest;

    private Orders order;


    private Double SpecialistSuggestion;
}
