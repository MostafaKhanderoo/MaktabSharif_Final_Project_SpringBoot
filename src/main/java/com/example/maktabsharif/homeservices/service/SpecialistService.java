package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;

import java.io.IOException;

public interface SpecialistService {
    UserDTO savaSpecialist(UserCreateDTO createDTO) throws IOException;
    UserDTO updateSpecialist(UserUpdateDTO updateDTO) throws IOException;
    UserDTO findById(Long id);
    void deleteById(Long id);
    User findByIdUser(Long id);

    User findSpecialistByFirstName(String firstName);
}
