package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import com.example.maktabsharif.homeservices.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {
    Optional<SubService> findByName(String name);
    boolean existsByNameAndService(String name, ServiceEntity service);
}
