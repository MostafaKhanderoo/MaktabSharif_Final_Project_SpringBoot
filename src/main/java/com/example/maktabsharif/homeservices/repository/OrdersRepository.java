package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
