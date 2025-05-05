package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.order.OrderRequestCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderRequestDTO;
import com.example.maktabsharif.homeservices.entity.OrderRequest;
import com.example.maktabsharif.homeservices.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderRequestService {
    OrderRequestDTO specialistRequestForOrder(OrderRequestCreateDTO orderRequestDTO);

    Optional<OrderRequestDTO> existsOrderRequestByOrderIdAndSpecialistId(Long orderId,Long specialistId);

     List<User> listSpecialistAcceptRequest(Long customerRequests);

    List<OrderRequestDTO> listSpecialistRequestForOrder(Long orderId);
}
