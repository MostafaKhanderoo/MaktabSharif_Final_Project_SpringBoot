package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderRequestDTO {
    private Long id;
    private User SpecialistAcceptRequest;


    private Orders order;


    private Double  SpecialistSuggestion;


}
