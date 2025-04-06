package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;

public interface CustomerService {
    UserDTO savaCustomer(UserCreateDTO createDTO);

    UserDTO updateCustomer(UserUpdateDTO updateDTO);

    UserDTO findByIdAndRole(Long id);

    void deleteById(Long id);

    User chooseLoginCustomerById(Long id);

    User findUserByFirstName(String firstName);
}
