package com.example.maktabsharif.homeservices.service.impl;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateRandomAddress {

    public String getRandomAddress() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
