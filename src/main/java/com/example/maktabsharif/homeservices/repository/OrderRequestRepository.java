package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequest,Long> {
}
