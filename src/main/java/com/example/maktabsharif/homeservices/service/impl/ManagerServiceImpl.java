package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.service.ManagerService;
import com.example.maktabsharif.homeservices.service.RoleService;
import com.example.maktabsharif.homeservices.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime ;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final UserService managerService;
    private final RoleService roleService;



    @Override
    public UserDTO savaManager(UserCreateDTO createDTO) throws IOException {
        User user = new User();
        user.setRegisterDate(LocalDateTime.now());
        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        user.setUsername(createDTO.username());
        user.setPassword(createDTO.password());
        user.setAge(createDTO.age());
        user.setEmail(createDTO.email());
        Role role =roleService.findByName(Role.ADMIN);
        user.getRole().add(role);
        user.setUserStatus(UserStatus.PENDING);


        var saveManager =managerService.saveUser(user);
        log.info("Manager with id {} saved", saveManager.getId());
        return getDtoFromManager(saveManager);
    }

    @Override
    public UserDTO updateManager(UserUpdateDTO updateDTO) throws IOException {


        User updateCustomer =managerService.findById(updateDTO.id());

        updateCustomer.setFirstname(updateDTO.firstname());
        updateCustomer.setLastname(updateDTO.lastname());

        if (updateDTO.age() >=18){
            updateCustomer.setAge(updateDTO.age());
        }else
            throw new InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


        updateCustomer.setUsername(updateDTO.username());
        updateCustomer.setEmail(updateDTO.email());
        updateCustomer.setPassword(updateDTO.password());
        updateCustomer.setUserImage(updateDTO.image().getBytes());

        User savaManager =managerService.updateUser(updateCustomer);
        log.info("Manager with id {} updated", savaManager.getId());
        return getDtoFromManager(savaManager);
    }

    @Override
    public UserDTO findById(Long id) {
      var manager=  managerService.findById(id);

        return getDtoFromManager(manager);
    }

    @Override
    public void deleteById(Long id) {
       managerService.deleteById(id);
    }

    @Override
    public User findManagerByFirstName(String firstName) {
        return null;
    }
    private UserDTO getDtoFromManager(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .registerDate(user.getRegisterDate())
                .profileImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }
}
