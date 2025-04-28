package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionCreateDTO;
import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionDTO;
import com.example.maktabsharif.homeservices.entity.Transaction;
import com.example.maktabsharif.homeservices.repository.TransactionRepository;
import com.example.maktabsharif.homeservices.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionCreateDTO.amount());
        transaction.setTimeTransaction(LocalDateTime.now());
        transaction.setCustomerValidity(transactionCreateDTO.customerValidity());
        transaction.setSpecialistValidity(transactionCreateDTO.SpecialistValidity());

        transactionRepository.save(transaction);
        log.info("Transaction with id{"+transaction.getId()+"} saved!");
        return getTransaction(transaction);

    }

    @Override
    public Optional<TransactionDTO> findTransactionById(Long id) {
        return Optional.empty();
    }

    private TransactionDTO getTransaction(Transaction transaction){
        return TransactionDTO.builder()
                .timeTransaction(transaction.getTimeTransaction())
                .amount(transaction.getAmount())
                .customerValidity(transaction.getCustomerValidity())
                .SpecialistValidity(transaction.getSpecialistValidity())
                .build();
    }
}
