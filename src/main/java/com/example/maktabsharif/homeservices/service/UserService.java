package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.Operator;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserDTO saveUser(UserCreateDTO createDTO, RoleName roleName)throws IOException;
    UserDTO updateUser(UserUpdateDTO updateDTO) throws IOException;

    UserDTO findByIdAndRole(Long id);

    void deleteById(Long id);

    Optional<User> chooseLoginUserById(Long id);

    User findUserByFirstName(String firstName);

    void  changePassword (Long userId,String currentPassword,String newPassword);


    List<UserDTO> searchUserByAge(Long age, Operator operator);

    List<UserDTO> searchUserByFieldAndValue(String field,String value);


    List<UserDTO> getAllUser();

}
