package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.enumeration.OrderStatus;

public record OrderCreateDTO(
        String subService,

        Long customerRequestService,

        double orderPriceRequest,


        OrderStatus orderStatus
) {
}
