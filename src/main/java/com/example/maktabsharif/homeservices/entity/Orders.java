package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import com.example.maktabsharif.homeservices.enumeration.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity<Long> {

    @ManyToOne
    private SubService subService;
    @ManyToOne
    private User customerRequestService;

    private double orderPriceRequest;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    private User specialistAccepted;

}
