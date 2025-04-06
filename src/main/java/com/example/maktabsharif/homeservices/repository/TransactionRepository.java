package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}

