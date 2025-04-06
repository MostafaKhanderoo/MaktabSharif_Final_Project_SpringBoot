package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SubServiceDTO {
    Long id;
    private String name;


    private Double basePrice;


    private String description;


    private ServiceEntity service;
}
