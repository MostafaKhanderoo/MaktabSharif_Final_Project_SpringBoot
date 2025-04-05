package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter

@NoArgsConstructor
public class SubService extends BaseEntity<Long> {

    private String name;


    private Double basePrice;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false,name = "service")
    private ServiceEntity service;

    public SubService(String name, Double basePrice, String description) {
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
    }
}
