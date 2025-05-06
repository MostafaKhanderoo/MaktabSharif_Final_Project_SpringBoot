package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.Transaction;
import com.example.maktabsharif.homeservices.entity.ValidityTransaction;
import com.example.maktabsharif.homeservices.repository.ValidityTransactionRepository;
import com.example.maktabsharif.homeservices.service.OrderService;
import com.example.maktabsharif.homeservices.service.TransactionService;
import com.example.maktabsharif.homeservices.service.ValidityTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ValidityTransactionServiceImpl implements ValidityTransactionService {
    private final ValidityTransactionRepository validityTransactionRepository;
    private final OrderService orderService;
    private final TransactionService transactionService;

    @Override
    public ValidityTransaction saveValidityTransaction(Long orderId, Long transactionId) {
       Orders order= orderService.findById(orderId).get();
        Transaction transaction = transactionService.findById(transactionId);
        ValidityTransaction validityTransaction = new ValidityTransaction();
        validityTransaction.setTransaction(transaction);
        validityTransaction.setOrders(order);
        log.info("Validity Transaction create for order id{"+orderId+"}" +
                " and transaction id{"+transactionId+"}");

      return validityTransactionRepository.save(validityTransaction);

    }
}
