package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.order.OrderCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderResponseDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (createDTO.subService() == null) {
            throw   new InvalidInputException("زیرسرویس نمی‌تواند خالی باشد",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);
        }

        if (createDTO.customerRequestService() == null) {
            throw new InvalidInputException("مشتری نمی‌تواند خالی باشد",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);
        }


        var service = serviceManagement.findSubServiceByName(createDTO.subService())
                .orElseThrow(() -> new InvalidInputException("زیرسرویس یافت نشد",
                        CustomApiExceptionType.NOT_FOUND));

        var customer = customerService.chooseLoginCustomerById(createDTO.customerRequestService())
                .orElseThrow(() -> new InvalidInputException("مشتری یافت نشد",
                        CustomApiExceptionType.NOT_FOUND));


        if (service.getBasePrice() > createDTO.orderPriceRequest()) {
            throw new InvalidInputException(
                    "قیمت پیشنهادی باید بیشتر یا مساوی قیمت پایه سرویس باشد. قیمت پایه: " + service.getBasePrice(),
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);
        }


        Orders order = new Orders();
        order.setOrderPriceRequest(createDTO.orderPriceRequest());
        order.setOrderStatus(OrderStatus.PENDING_OFFERS);
        order.setCustomerRequestService(customer);
        order.setSubService(service);


        var orderSaved = ordersRepository.save(order);

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

    @Override
    public List<OrderResponseDTO> getAllNullSpecialistOrder() {
        var ordersList= ordersRepository.getListOrderSpecialistNull();
        return ordersList.stream()
                .map(orders ->new  OrderResponseDTO(
                        orders.getId(),
                        orders.getSubService().getService().getName(),
                        orders.getSubService().getName() ,
                        orders.getCustomerRequestService().getFirstname(),
                        orders.getOrderPriceRequest(),
                        orders.getOrderStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> ordersOfCustomer(Long customerRequestedId) {
        var ordersCustomer =
                ordersRepository.getOrdersByCustomerRequestServiceId(customerRequestedId);
        return ordersCustomer.stream()
                .map(orders -> new OrderResponseDTO(
                        orders.getId(),
                        orders.getSubService().getService().getName(),
                        orders.getSubService().getName() ,
                        orders.getCustomerRequestService().getFirstname(),
                        orders.getOrderPriceRequest(),
                        orders.getOrderStatus()))
                .collect(Collectors.toList());

    }

    @Override
    public OrderDTO customerChooseSpecialist(Long orderId, Long specialistId) {
        var orderChooses= findById(orderId).get();
        var specialistAccept =specialistService.findByIdUser(specialistId);

       orderChooses.setOrderStatus(OrderStatus.AWAITING_ARRIVAL);
       orderChooses.setSpecialistAccepted(specialistAccept);

       ordersRepository.save(orderChooses);
       log.info(specialistAccept.getFirstname()+" accepted for order id {"+orderId+"}");
       return getDtoFromOrder(orderChooses);


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
