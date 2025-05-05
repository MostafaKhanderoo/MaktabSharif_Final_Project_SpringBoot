package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.entity.ValidityTransaction;

public interface ValidityTransactionService {

    ValidityTransaction saveValidityTransaction(Long orderId, Long transactionId);
}
