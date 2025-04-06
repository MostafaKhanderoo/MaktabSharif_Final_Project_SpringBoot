package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;

public interface ManagerService {
    UserDTO savaManager(UserCreateDTO createDTO);
    UserDTO updateManager(UserUpdateDTO updateDTO);
    UserDTO findById(Long id);
    void deleteById(Long id);

    User findManagerByFirstName(String firstName);
}
