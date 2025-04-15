package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SubServiceDTO {
    Long id;
    private String name;


    private Double basePrice;


    private String description;


    private ServiceEntity service;
}
