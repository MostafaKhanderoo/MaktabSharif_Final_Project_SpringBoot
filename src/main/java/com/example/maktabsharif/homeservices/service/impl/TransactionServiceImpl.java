package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionCreateDTO;
import com.example.maktabsharif.homeservices.dto.transactionorder.TransactionDTO;
import com.example.maktabsharif.homeservices.entity.Transaction;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.TransactionRepository;
import com.example.maktabsharif.homeservices.service.OrderService;
import com.example.maktabsharif.homeservices.service.TransactionService;
import com.example.maktabsharif.homeservices.service.ValidityTransactionService;
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
   // private final ValidityTransactionService validityTransactionService;
    private final OrderService orderService;
    @Override
    public TransactionDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionCreateDTO.amount());
        transaction.setTimeTransaction(LocalDateTime.now());
        transaction.setCustomerValidity(transactionCreateDTO.customerValidity());
        transaction.setSpecialistValidity(transactionCreateDTO.specialistValidity());
       var orderSelected= orderService.findById(transactionCreateDTO.orderSelected()).get();
        transaction.setOrders(orderSelected);

       var transactionCreated= transactionRepository.save(transaction);
   //    validityTransactionService.saveValidityTransaction(transactionCreateDTO.orderId(),transactionCreated.getId());
        log.info("Transaction with id{"+transaction.getId()+"} saved!");
        return getTransaction(transactionCreated);

    }

    @Override
    public Optional<TransactionDTO> findTransactionById(Long id) {
        return Optional.empty();
    }

    @Override
    public Transaction findById(Long id) {
         if(transactionRepository.findById(id).isEmpty())
             throw new NotFoundException("Transaction with id{"+id+"} not found",
                     CustomApiExceptionType.NOT_FOUND);
         return transactionRepository.findById(id).get();
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
