package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> , JpaSpecificationExecutor<User> {

    Optional<User> findById(Long id);
    Boolean existsUserByUsernameAndRole(String username, RoleName role);
    Optional <User> findUserByIdAndRole(Long id, RoleName role);
    List<User> findUserByRole(RoleName role);




    User findUserByRoleAndFirstname(RoleName role , String firstName);
    Optional<User>findByUsername(String username);
    Optional<User>findByEmail(String email);
    List<User> findAllByRole(RoleName role);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    void deleteById(Long id);

}
