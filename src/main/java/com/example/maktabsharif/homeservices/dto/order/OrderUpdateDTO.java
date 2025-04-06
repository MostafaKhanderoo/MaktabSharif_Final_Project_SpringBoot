package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.entity.SubService;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.OrderStatus;

public record OrderUpdateDTO(
        Long id,
        SubService subService,

        User customerRequestService,

        double orderPriceRequest,


        OrderStatus orderStatus
) {
}
