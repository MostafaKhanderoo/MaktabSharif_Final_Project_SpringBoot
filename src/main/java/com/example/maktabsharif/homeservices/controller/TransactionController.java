package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionCreateDTO;
import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionDTO;
import com.example.maktabsharif.homeservices.service.PaymentOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final PaymentOrder paymentOrder;

    @PostMapping("/payment/order")
    public ResponseEntity<TransactionDTO> payOrder(@RequestBody TransactionCreateDTO transactionCreateDTO) {

        return ResponseEntity.ok(paymentOrder.payOrder(transactionCreateDTO));

    }
}
