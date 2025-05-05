package com.example.maktabsharif.homeservices.dto.order;

import com.example.maktabsharif.homeservices.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderResponseDTO {
    private Long id;
    private String serviceName;
    private String subService;

    private String customerRequestService;

    private double orderPriceRequest;


    private OrderStatus orderStatus;
}
