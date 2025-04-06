package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    boolean existsServiceEntitiesByName(String name);

    Optional<ServiceEntity> findByName(String serviceName);
}
