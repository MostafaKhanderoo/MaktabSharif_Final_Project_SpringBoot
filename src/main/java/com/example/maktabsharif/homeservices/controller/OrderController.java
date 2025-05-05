package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.order.OrderCreateDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderDTO;
import com.example.maktabsharif.homeservices.dto.order.OrderResponseDTO;
import com.example.maktabsharif.homeservices.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService  orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateDTO createDTO){
        return   ResponseEntity
                .ok(orderService.addNewOrder(createDTO));
    }

    @GetMapping("/order/list")
    public ResponseEntity<List<OrderResponseDTO>> getListNullSpecialist(){
        return ResponseEntity.ok(orderService.getAllNullSpecialistOrder());
    }

    @GetMapping("/list/customer/order/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(@PathVariable Long customerId){
       return ResponseEntity.ok(orderService.ordersOfCustomer(customerId));
    }

    @PostMapping("/accept/specialist/request")
    public ResponseEntity<OrderDTO> acceptSpecialistRequest(
            @RequestParam Long orderId,
            @RequestParam Long specialistId) {
        return ResponseEntity.ok(orderService.customerChooseSpecialist(orderId, specialistId));
    }

}
