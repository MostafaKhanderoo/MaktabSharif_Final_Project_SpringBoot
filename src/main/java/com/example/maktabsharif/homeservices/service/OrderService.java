package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.order.OrderCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderResponseDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderDTO addNewOrder(OrderCreateDTO createDTO);
    OrderDTO updateOrder(OrderUpdateDTO updateDTO);
    Optional<Orders> findById(Long id);
    OrderDTO findOrderById(Long id);
    void  deleteOrderById(Long id);
    List<OrderResponseDTO> getAllNullSpecialistOrder();

    List<OrderResponseDTO>ordersOfCustomer(Long customerRequestedId);

    OrderDTO customerChooseSpecialist(Long orderId,Long specialistId);
}
