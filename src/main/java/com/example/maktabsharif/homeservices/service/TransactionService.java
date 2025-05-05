package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionCreateDTO;
import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionDTO;
import com.example.maktabsharif.homeservices.entity.Transaction;

import java.util.Optional;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionCreateDTO transactionCreateDTO);
    Optional<TransactionDTO>findTransactionById(Long id);
    Transaction findById(Long id);
}
