package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.entity.bank.CreditCard;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.repository.CreditCardRepository;
import com.example.maktabsharif.homeservices.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private  final CreditCardRepository creditCardRepository;


    @Override
    public CreditCard creatCard(CreditCard creditCard) {
        if (creditCardRepository.findCreditCardByCardNumber(creditCard.getCardNumber()).isPresent())
            throw new ExistsException("credit card with number{"+creditCard.getCardNumber()+"} Exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        CreditCard requestCard=new CreditCard();
        requestCard.setCardNumber(creditCard.getCardNumber());
        requestCard.setDataCard(LocalDateTime.now());
        requestCard.setPassword(creditCard.getPassword());

        creditCardRepository.save(requestCard);
        return requestCard;


    }

    @Override
    public CreditCard updateCard(CreditCard creditCard) {
        return null;
    }

    @Override
    public CreditCard findCardById(Long id) {
        return null;
    }

    @Override
    public boolean creditCardExist(String username) {
        return false;
    }

    @Override
    public CreditCard findCardByCardNumber(String cardNumber) {
        return null;
    }
}
