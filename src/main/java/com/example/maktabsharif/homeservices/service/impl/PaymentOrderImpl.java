package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionCreateDTO;
import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionDTO;
import com.example.maktabsharif.homeservices.entity.Validity;
import com.example.maktabsharif.homeservices.service.PaymentOrder;
import com.example.maktabsharif.homeservices.service.TransactionService;
import com.example.maktabsharif.homeservices.service.ValidityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PaymentOrderImpl implements PaymentOrder {
    private final ValidityService validityService;
    private final TransactionService transactionService;


    @Override
    public TransactionDTO payOrder(TransactionCreateDTO transactionCreateDTO) {
        Validity customerValidity =
                validityService.findValidityByAddress(transactionCreateDTO.customerValidity()).get();

        Validity specialistValidity =
                validityService.findValidityByAddress(transactionCreateDTO.specialistValidity()).get();

        if (customerValidity.getBalance() < transactionCreateDTO.amount()) {
            throw new IndexOutOfBoundsException("Customer does not have enough money to pay!");
        }

        customerValidity.setBalance(customerValidity.getBalance() - transactionCreateDTO.amount());
        specialistValidity.setBalance(specialistValidity.getBalance() + transactionCreateDTO.amount());
        validityService.updateValidity(customerValidity);
        validityService.updateValidity(specialistValidity);

        log.info("transaction created for order id{" + transactionCreateDTO.orderSelected() + "}");
        return transactionService.createTransaction(transactionCreateDTO);
    }
}
