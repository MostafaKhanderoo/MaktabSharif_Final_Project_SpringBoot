package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.ValidityTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidityTransactionRepository extends JpaRepository<ValidityTransaction,Long> {
}
