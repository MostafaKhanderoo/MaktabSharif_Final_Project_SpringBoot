package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.entity.Validity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidityRepository extends JpaRepository<Validity,Long> {

    Optional<Validity> findValidityByAddress(String address);
    Optional<Validity>findValidityByUser(User user);
    boolean existsValidityByUser(User user);
    void deleteValidityByUserId(Long id);
    Optional<Validity>findValidityByUserId(Long id);
}

