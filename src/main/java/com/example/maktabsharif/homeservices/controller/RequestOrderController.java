package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.order.OrderRequestCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderRequestDTO;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.service.OrderRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("requestorder")
@RequiredArgsConstructor
public class RequestOrderController {
    private  final OrderRequestService orderRequest;

    @PostMapping("/request")
    public ResponseEntity<OrderRequestDTO> addRequestForOrder(@RequestBody OrderRequestCreateDTO requestDTO){
        return ResponseEntity
                .ok(orderRequest.specialistRequestForOrder(requestDTO));

    }
    @GetMapping("/ordercustomer/{customerId}")
    public ResponseEntity<List<User>> allOrderOfCustomer(@PathVariable Long customerId){
     return ResponseEntity
                .ok(orderRequest.listCustomerRequest(customerId));
    }
}
