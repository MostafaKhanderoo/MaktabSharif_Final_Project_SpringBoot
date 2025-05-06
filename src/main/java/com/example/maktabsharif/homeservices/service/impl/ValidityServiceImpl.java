package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.authentication.AuthenticationUser;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.entity.Validity;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.ValidityRepository;
import com.example.maktabsharif.homeservices.service.ValidityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ValidityServiceImpl implements ValidityService {

    private final ValidityRepository validityRepository;
    private final GenerateRandomAddress generateRandomAddress;

    @Transactional
    @Override
    public Validity createValidityForUser(User user) {


        existsValidityForUser(user);
        Validity validityRequest = new Validity();
        validityRequest.setAddress(generateRandomAddress.getRandomAddress());
        validityRequest.setAccountLock(true);
        validityRequest.setBalance(0.0);
        validityRequest.setUser(user);

        validityRepository.save(validityRequest);
        log.info("validity for user "+user.getFirstname()+" saved");
        return validityRequest;
    }

    @Override
    public Validity updateValidity(Validity validity) {

        Validity existingValidity = validityRepository.findById(validity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Validity not found with id: " + validity.getId()));


        existingValidity.setAddress(validity.getAddress());
        existingValidity.setAccountLock(validity.isAccountLock());
        existingValidity.setBalance(validity.getBalance());
        existingValidity.setUser(validity.getUser());


        Validity updatedValidity = validityRepository.save(existingValidity);
        log.info("Validity with id {} updated successfully", validity.getId());

        return updatedValidity;
    }

    @Override
    public Optional<Validity> findValidityById(Long id) {
        return null;
    }

    @Override
    public void deleteValidityById(Long id) {

    }

    @Override
    public Optional<Validity> findValidityByAddress(String address) {
     if (validityRepository.findValidityByAddress(address).isEmpty())
     throw new NotFoundException("validity not found for address{"+address+"} not found!",
             CustomApiExceptionType.NOT_FOUND);
     return validityRepository.findValidityByAddress(address);
    }

    @Override
    public Optional<Validity> findValidityByUser(User user) {
        return Optional.empty();
    }

    @Override
    public void existsValidityForUser(User user) {
        var loggedUser = AuthenticationUser.getLoggedInUser();
        if (validityRepository.existsValidityByUser(loggedUser))
            throw new ExistsException("validity exists for " + loggedUser.getFirstname(),
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

    }

    @Override
    public Optional<Validity> findValidityByUserId(Long id) {
        if (validityRepository.findValidityByUserId(id).isEmpty())
            throw new NotFoundException("validity not found for user id{"+id+"} not found!" ,
                    CustomApiExceptionType.NOT_FOUND);

        return validityRepository.findValidityByUserId(id);
    }

    @Override
    public void deleteValidityByUserId(Long id) {
        findValidityByUserId(id);
        validityRepository.deleteValidityByUserId(id);
        log.info("validity of user id{"+id+"} deleted");
    }

    @Override
    public Validity updateValidityBalance(Validity validity) {
return null;
    }

    @Override
    public Validity findById(Long id) {
        if (validityRepository.findValidityByUserId(id).isEmpty())
            throw new NotFoundException("validity not found for user id{"+id+"} not found!" ,
                    CustomApiExceptionType.NOT_FOUND);
        return validityRepository.findById(id).get();
    }


}
