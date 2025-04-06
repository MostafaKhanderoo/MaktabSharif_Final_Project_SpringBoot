package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.order.OrderCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderUpdateDTO;

public interface OrderService {

    OrderDTO addNewOrder(OrderCreateDTO createDTO);
    OrderDTO updateOrder(OrderUpdateDTO updateDTO);
    OrderDTO findOrderById(Long id);
    void  deleteOrderById(Long id);
}
