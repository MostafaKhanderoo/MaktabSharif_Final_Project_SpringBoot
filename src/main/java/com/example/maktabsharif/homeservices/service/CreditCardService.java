package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.entity.bank.CreditCard;

public interface CreditCardService {
    CreditCard creatCard(CreditCard creditCard);
    CreditCard updateCard(CreditCard creditCard);
    CreditCard findCardById(Long id);
    boolean creditCardExist(String username);

    CreditCard findCardByCardNumber(String cardNumber);
}
