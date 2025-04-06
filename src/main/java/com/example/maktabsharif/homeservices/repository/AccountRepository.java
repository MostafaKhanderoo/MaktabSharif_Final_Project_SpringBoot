package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.bank.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
