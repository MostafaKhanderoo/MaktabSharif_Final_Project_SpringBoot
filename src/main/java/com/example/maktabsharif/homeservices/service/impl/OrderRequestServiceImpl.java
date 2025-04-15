package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.order.OrderRequestCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderRequestDTO;
import com.example.maktabsharif.homeservices.entity.OrderRequest;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.repository.OrderRequestRepository;
import com.example.maktabsharif.homeservices.service.CustomerService;
import com.example.maktabsharif.homeservices.service.OrderRequestService;
import com.example.maktabsharif.homeservices.service.OrderService;
import com.example.maktabsharif.homeservices.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderRequestServiceImpl implements OrderRequestService {
    private  final OrderRequestRepository ordersRepository;
    private final SpecialistService specialistService;
    private final OrderService orderService;
    private final CustomerService customerService;



    @Override
    public OrderRequestDTO specialistRequestForOrder(OrderRequestCreateDTO orderRequestDTO) {

        orderService.findById(orderRequestDTO.order());
        var specialist= specialistService.findByIdUser
                (orderRequestDTO.SpecialistAcceptRequest());

        var order= orderService.findById(orderRequestDTO.order());
        if(ordersRepository.findOrderRequestByOrderIdAndSpecialistAcceptRequestId(orderRequestDTO.order(),
                orderRequestDTO.SpecialistAcceptRequest()).isPresent())
            throw new ExistsException("request is exists for order with id{" +orderRequestDTO.order()+
                    "}and specialist id{"+orderRequestDTO.SpecialistAcceptRequest()+"}",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrder(order.get());
        orderRequest.setSpecialistAcceptRequest(specialist);
        orderRequest.setSpecialistSuggestion(orderRequestDTO.SpecialistSuggestion());
        ordersRepository.save(orderRequest);
        log.info("Request add for order{"+orderRequestDTO.order()+
                "and specialist{"+orderRequestDTO.SpecialistAcceptRequest());
        return getOrderRequest(orderRequest);

    }

    @Override
    public Optional<OrderRequestDTO> existsOrderRequestByOrderIdAndSpecialistId(Long orderId, Long specialistId) {
        return Optional.empty();
    }

    @Override
    public List<OrderRequestDTO> listSpecialistRequestForOrder(Long userId, Long orderId) {
        return null;
    }

    @Override
    public List <User> listCustomerRequest(Long customerRequests) {
      var customerRequest=  customerService.chooseLoginCustomerById(customerRequests);
        var listOrderRequest=
                ordersRepository.findAllByCustomerRequestService(customerRequests);
       return listOrderRequest;
    }

    private OrderRequestDTO getOrderRequest(OrderRequest orderRequest){
      return   OrderRequestDTO.builder()
                .order(orderRequest.getOrder())
                .SpecialistAcceptRequest(orderRequest.getSpecialistAcceptRequest())
                .SpecialistSuggestion(orderRequest.getSpecialistSuggestion())
                .build();
    }
}
