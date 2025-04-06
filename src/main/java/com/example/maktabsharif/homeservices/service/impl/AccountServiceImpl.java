package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.repository.AccountRepository;
import com.example.maktabsharif.homeservices.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public boolean checkCustomerAccount(Long id) {
        return false;
    }

    @Override
    public AccountService findAccountCustomerLogged(Long id) {
        return null;
    }

    @Override
    public AccountService findAccountByCreditCard(String cardNumber) {
        return null;
    }

    @Override
    public void deposit(String address, Double amount) {

    }

    @Override
    public void withdraw(String address, Double amount) {

    }
}
