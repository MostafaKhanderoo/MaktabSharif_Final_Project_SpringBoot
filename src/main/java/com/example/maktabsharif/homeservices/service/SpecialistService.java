package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;

public interface SpecialistService {
    UserDTO savaSpecialist(UserCreateDTO createDTO);
    UserDTO updateSpecialist(UserUpdateDTO updateDTO);
    UserDTO findById(Long id);
    void deleteById(Long id);

    User findSpecialistByFirstName(String firstName);
}
