package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role>findByName(String name);
}
