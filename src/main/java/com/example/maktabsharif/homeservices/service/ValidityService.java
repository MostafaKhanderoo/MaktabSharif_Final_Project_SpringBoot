package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.entity.Validity;

import java.util.Optional;

public interface ValidityService {
    Validity createValidityForUser(User user);
    Validity updateValidity(Validity validity);
    Optional<Validity> findValidityById(Long id);
    void deleteValidityById(Long id);
    Optional<Validity>findValidityByAddress(String address);
    Optional<Validity>findValidityByUser(User user);
    void existsValidityForUser(User user);
    Optional<Validity>findValidityByUserId(Long id);
    void deleteValidityByUserId(Long id);

}
