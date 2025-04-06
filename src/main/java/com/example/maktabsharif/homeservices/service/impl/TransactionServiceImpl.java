package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.repository.TransactionRepository;
import com.example.maktabsharif.homeservices.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
}
