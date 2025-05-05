package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.entity.SubService;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.OrderStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private SubService subService;

    private User customerRequestService;

    private double orderPriceRequest;


    private OrderStatus orderStatus;
}