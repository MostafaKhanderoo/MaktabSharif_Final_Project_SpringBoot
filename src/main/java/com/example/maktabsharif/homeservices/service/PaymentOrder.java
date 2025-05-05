package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionCreateDTO;
import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionDTO;

public interface PaymentOrder {

    TransactionDTO payOrder( TransactionCreateDTO transactionCreateDTO);
}
