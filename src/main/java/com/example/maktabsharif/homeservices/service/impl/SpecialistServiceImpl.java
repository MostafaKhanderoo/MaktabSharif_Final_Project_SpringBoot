package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.user.SpecialistCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.repository.RoleRepository;
import com.example.maktabsharif.homeservices.service.RoleService;
import com.example.maktabsharif.homeservices.service.SpecialistService;
import com.example.maktabsharif.homeservices.service.UserService;
import com.example.maktabsharif.homeservices.service.ValidityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final UserService specialistService;
    private final RoleService roleService;
    private final ValidityService validityService;

    @Override
    public UserDTO savaSpecialist(SpecialistCreateDTO createDTO) throws IOException {
        User user = new User();
        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        user.setUsername(createDTO.username());
        user.setPassword(createDTO.password());
        if (createDTO.age() >=18){
            user.setAge(createDTO.age());
        }else
            throw new   InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        user.setEmail(createDTO.email());
        user.setRegisterDate(LocalDateTime.now());
        Role role =roleService.findByName(Role.EXPERT);
        user.getRole().add(role);
        user.setUserStatus(UserStatus.PENDING);

        var saveCustomer =specialistService.saveUser(user);
        validityService.createValidityForUser(user);
        log.info("Specialist with id {} saved", saveCustomer.getId());
        return getDtoFromSpecialist(saveCustomer);


    }

    @Override
    public UserDTO updateSpecialist(UserUpdateDTO updateDTO) throws IOException {
        specialistService.findById(updateDTO.id());

        User user = specialistService.chooseLoginUserById(updateDTO.id());

        user.setFirstname(updateDTO.firstname());
        user.setLastname(updateDTO.lastname());

        if (updateDTO.age() >=18){
            user.setAge(updateDTO.age());
        }else
            throw new   InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);



        user.setUsername(updateDTO.username());
        user.setEmail(updateDTO.email());
        user.setPassword(updateDTO.password());
        user.setUserImage(updateDTO.image().getBytes());

        User savedCustomer =specialistService.updateUser(user);
        log.info("Customer with id {} updated", savedCustomer.getId());
        return getDtoFromSpecialist(savedCustomer);

    }

    @Override
    public UserDTO findById(Long id) {
     var specialist=   specialistService.findById(id);
        return getDtoFromSpecialist(specialist);
    }

    @Override
    public User findByIdUser(Long id) {
     return   specialistService.findById(id);
    }

    @Override
    public void deleteById(Long id) {
       specialistService.deleteById(id);

    }

    @Override
    public User findSpecialistByFirstName(String firstName) {
        return null;
    }

    private UserDTO getDtoFromSpecialist(User user) {
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
