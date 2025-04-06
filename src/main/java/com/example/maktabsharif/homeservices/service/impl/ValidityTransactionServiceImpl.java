package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.repository.ValidityTransactionRepository;
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
    private  final ValidityTransactionRepository validityTransactionRepository;
}
