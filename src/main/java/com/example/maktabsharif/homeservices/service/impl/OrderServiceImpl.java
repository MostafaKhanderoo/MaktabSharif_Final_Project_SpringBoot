package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.order.OrderCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.enumeration.OrderStatus;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.OrderRequestRepository;
import com.example.maktabsharif.homeservices.repository.OrdersRepository;
import com.example.maktabsharif.homeservices.service.CustomerService;
import com.example.maktabsharif.homeservices.service.OrderService;
import com.example.maktabsharif.homeservices.service.ServiceManagementService;
import com.example.maktabsharif.homeservices.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final ServiceManagementService serviceManagement;
    private final CustomerService customerService;
    private final OrderRequestRepository orderRequestService;
    private final SpecialistService specialistService;
    @Override
    public OrderDTO addNewOrder(OrderCreateDTO createDTO) {
        var service = serviceManagement.findSubServiceByName(createDTO.subService());
       var customerLogg = customerService.chooseLoginCustomerById(createDTO.customerRequestService());
        Orders order = new Orders();


        if (service.getBasePrice() < createDTO.orderPriceRequest())
            throw new InvalidInputException("price request must equals or must be more than service price.",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        order.setOrderPriceRequest(createDTO.orderPriceRequest());
        order.setOrderStatus(OrderStatus.PENDING_OFFERS);
        order.setCustomerRequestService(customerLogg);
        order.setSubService(service);

        var orderSaved= ordersRepository.save(order);

        log.info("customer with id{"+orderSaved.getId()+"} saved");
        return getDtoFromOrder(orderSaved);

    }

    @Override
    public OrderDTO updateOrder(OrderUpdateDTO updateDTO) {

        if (ordersRepository.findById(updateDTO.id()).isEmpty())
            throw new NotFoundException("order with id{"+
                    updateDTO.id()+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        var customerOrder =ordersRepository.findById(updateDTO.id()).get();


        customerOrder.setOrderStatus(updateDTO.orderStatus());
        customerOrder.setSubService(updateDTO.subService());
        customerOrder.setOrderPriceRequest(updateDTO.orderPriceRequest());

        ordersRepository.save(customerOrder);
        return getDtoFromOrder(customerOrder);



    }

    @Override
    public Optional<Orders> findById(Long id) {
        if (ordersRepository.findById(id).isEmpty())
            throw new NotFoundException("order with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return ordersRepository.findById(id);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        if (ordersRepository.findById(id).isEmpty())
            throw new NotFoundException("order with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return getDtoFromOrder(ordersRepository.findById(id).get());

    }

    @Override
    public void deleteOrderById(Long id) {
        if (ordersRepository.findById(id).isEmpty())
            throw new NotFoundException("order with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        log.info("order with id{"+id+"} not deleted");
        ordersRepository.deleteById(id);

    }



    private OrderDTO getDtoFromOrder(Orders order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerRequestService(order.getCustomerRequestService())
                .subService(order.getSubService())
                .orderPriceRequest(order.getOrderPriceRequest())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
