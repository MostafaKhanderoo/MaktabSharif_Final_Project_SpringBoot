package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;

import java.io.IOException;

public interface ManagerService {
    UserDTO savaManager(UserCreateDTO createDTO) throws IOException;
    UserDTO updateManager(UserUpdateDTO updateDTO) throws IOException;
    UserDTO findById(Long id);
    void deleteById(Long id);

    User findManagerByFirstName(String firstName);
}
