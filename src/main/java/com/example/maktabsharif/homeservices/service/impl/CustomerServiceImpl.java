package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.Role;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.UserRepository;
import com.example.maktabsharif.homeservices.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final ValidityServiceImpl validityService;

    @Override
    public UserDTO savaCustomer(UserCreateDTO createDTO) {
        if (userRepository.existsUserByUsernameAndRole(createDTO.username(), Role.CUSTOMER))
            throw new ExistsException("Customer with username {"
                    + createDTO.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


        User user = new User();
        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        if (createDTO.age() >=18){
            user.setAge(createDTO.age());
        }else
            throw new InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);
        user.setAge(createDTO.age());
        user.setUsername(createDTO.username());
        user.setPassword(createDTO.password());
        user.setEmail(createDTO.email());
        user.setRegisterDate(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);
        user.setUserStatus(UserStatus.PENDING);
        user.setUserImage(createDTO.userImage());

        var saveCustomer =userRepository.save(user);
        validityService.createValidityForUser(user);
        log.info("Customer with id {} saved", saveCustomer.getId());
        return getDtoFromCustomer(saveCustomer);
    }

    @Override
    public UserDTO updateCustomer(UserUpdateDTO updateDTO) {
        if (userRepository.findUserById(updateDTO.id()).isEmpty())
            throw new NotFoundException("Customer with id {"
                    + updateDTO.id() + "} not found!",
                    CustomApiExceptionType.NOT_FOUND);

        User updateCustomer = userRepository.findUserById(updateDTO.id()).get();

        updateCustomer.setFirstname(updateDTO.firstname());
        updateCustomer.setLastname(updateDTO.lastname());

        if (updateDTO.age() >=18){
            updateCustomer.setAge(updateDTO.age());
        }else
            throw new   InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        if (userRepository.existsUserByUsernameAndRole(updateDTO.username(),Role.CUSTOMER))
            throw new ExistsException("Customer with username {"
                    + updateDTO.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        updateCustomer.setUsername(updateDTO.username());
        updateCustomer.setEmail(updateDTO.email());
        updateCustomer.setPassword(updateDTO.password());
        updateCustomer.setUserImage(updateDTO.image());

        User savedCustomer =userRepository.save(updateCustomer);
        log.info("Customer with id {} updated", savedCustomer.getId());
        return getDtoFromCustomer(savedCustomer);

    }

    @Override
    public UserDTO findByIdAndRole(Long id) {
        Optional<User> customer =userRepository.findUserByIdAndRole(id,Role.CUSTOMER);
        if (customer.isEmpty())
            throw new NotFoundException("Customer with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return getDtoFromCustomer(customer.get());
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findUserByIdAndRole(id,Role.CUSTOMER).isEmpty())
            throw new NotFoundException("Customer with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);


        validityService.deleteValidityByUserId(id);
        userRepository.deleteById(id);
        log.info("customer with id{"+id+"} deleted!");

    }

    @Override
    public User chooseLoginCustomerById(Long id) {
        var customerLog =userRepository.findUserByIdAndRole(id,Role.CUSTOMER);
        if (customerLog.isEmpty())
            throw new NotFoundException("Customer with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return  customerLog.get();
    }

    @Override
    public User findUserByFirstName(String firstName) {
        return   userRepository.findUserByRoleAndFirstname(Role.CUSTOMER,firstName);
    }

    private UserDTO getDtoFromCustomer(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .registerDate(user.getRegisterDate())
                .role(user.getRole())
                .userImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }
}
