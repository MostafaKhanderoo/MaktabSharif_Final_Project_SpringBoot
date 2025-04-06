package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserById(Long id);
    Boolean existsUserByUsernameAndRole(String username, Role role);
    Optional <User> findUserByIdAndRole(Long id, Role role);
    List<User> findUserByRole(Role role);



    User findUserByRoleAndFirstname(Role role ,String firstName);
}
