package com.example.maktabsharif.homeservices.service;

public interface AccountService {
    boolean checkCustomerAccount(Long id);


    AccountService findAccountCustomerLogged(Long id);

    AccountService findAccountByCreditCard(String cardNumber);

    void deposit (String address,Double amount);
    void withdraw (String address,Double amount);
}
