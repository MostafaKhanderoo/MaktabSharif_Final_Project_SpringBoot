package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.RoleRepository;
import com.example.maktabsharif.homeservices.repository.UserRepository;
import com.example.maktabsharif.homeservices.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository managerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDTO savaManager(UserCreateDTO createDTO) throws IOException {
        if (managerRepository.existsUserByUsernameAndRole(createDTO.username(), RoleName.MANAGER))
            throw new ExistsException("Manager with username {"
                    + createDTO.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        User user = new User();
        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        user.setAge(createDTO.age());
        user.setUsername(createDTO.username());
        user.setPassword(passwordEncoder.encode(createDTO.password()));
        user.setEmail(createDTO.email());
        user.setRegisterDate(LocalDateTime.now());

        Role role =roleRepository.findByName(RoleName.MANAGER)
                .orElseThrow(()->new NotFoundException(CustomApiExceptionType.NOT_FOUND));
        user.setRole(role);
        user.setUserStatus(UserStatus.PENDING);
        user.setUserImage(createDTO.profileImage().getBytes());

        var saveManager =managerRepository.save(user);
        log.info("Manager with id {} saved", saveManager.getId());
        return getDtoFromManager(saveManager);
    }

    @Override
    public UserDTO updateManager(UserUpdateDTO updateDTO) throws IOException {
        if (managerRepository.findUserByIdAndRole(updateDTO.id(), RoleName.MANAGER).isEmpty())
            throw new NotFoundException("Customer with id {"
                    + updateDTO.id() + "} not found!",
                    CustomApiExceptionType.NOT_FOUND);

        User updateCustomer = managerRepository.findUserById(updateDTO.id()).get();

        updateCustomer.setFirstname(updateDTO.firstname());
        updateCustomer.setLastname(updateDTO.lastname());

        if (updateDTO.age() >=18){
            updateCustomer.setAge(updateDTO.age());
        }else
            throw new InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        if (managerRepository.existsUserByUsernameAndRole(updateDTO.username(), RoleName.CUSTOMER))
            throw new ExistsException("Manager with username {"
                    + updateDTO.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        updateCustomer.setUsername(updateDTO.username());
        updateCustomer.setEmail(updateDTO.email());
        updateCustomer.setPassword(updateDTO.password());
        updateCustomer.setUserImage(updateDTO.image().getBytes());

        User savaManager =managerRepository.save(updateCustomer);
        log.info("Manager with id {} updated", savaManager.getId());
        return getDtoFromManager(savaManager);
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> manager =managerRepository.findUserByIdAndRole(id , RoleName.MANAGER);

        if (manager.isEmpty())
            throw new NotFoundException("Manager with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return getDtoFromManager(manager.get());
    }

    @Override
    public void deleteById(Long id) {
        if(managerRepository.findUserByIdAndRole(id, RoleName.MANAGER).isEmpty())
            throw new NotFoundException("Manager with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        managerRepository.deleteById(id);

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
                .role(user.getRole())
                .profileImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }
}
