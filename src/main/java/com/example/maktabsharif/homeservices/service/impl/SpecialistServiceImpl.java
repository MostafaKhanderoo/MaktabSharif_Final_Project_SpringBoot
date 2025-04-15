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
import com.example.maktabsharif.homeservices.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final UserRepository specialistRepository;
    @Override
    public UserDTO savaSpecialist(UserCreateDTO createDTO) {
        if (specialistRepository.existsUserByUsernameAndRole(createDTO.username(), Role.SPECIALIST))
            throw new ExistsException("specialist with username{"+createDTO.username()+"} already exists!",
                    CustomApiExceptionType.INTERNAL_SERVER_ERROR);

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
        user.setRole(Role.SPECIALIST);
        user.setUserStatus(UserStatus.PENDING);
        user.setUserImage(createDTO.userImage());

        var saveCustomer =specialistRepository.save(user);
        log.info("specialist with id {} saved", saveCustomer.getId());
        return getDtoFromSpecialist(saveCustomer);


    }

    @Override
    public UserDTO updateSpecialist(UserUpdateDTO updateDTO) {
        if (specialistRepository.findUserByIdAndRole(updateDTO.id(),Role.SPECIALIST).isEmpty())
            throw new NotFoundException("specialist with id {"
                    + updateDTO.id() + "} not found!",
                    CustomApiExceptionType.NOT_FOUND);

        User updateSpecialist = specialistRepository.findUserById(updateDTO.id()).get();

        updateSpecialist.setFirstname(updateDTO.firstname());
        updateSpecialist.setLastname(updateDTO.lastname());

        if (updateDTO.age() >=18){
            updateSpecialist.setAge(updateDTO.age());
        }else
            throw new InvalidInputException("Age must be 18 or older!"
                    ,CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        if (specialistRepository.existsUserByUsernameAndRole(updateDTO.username(),Role.SPECIALIST))
            throw new ExistsException("specialist with username {"
                    + updateDTO.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        updateSpecialist.setUsername(updateDTO.username());
        updateSpecialist.setEmail(updateDTO.email());
        updateSpecialist.setPassword(updateDTO.password());
        updateSpecialist.setUserImage(updateDTO.image());

        User saveSpecialist =specialistRepository.save(updateSpecialist);
        log.info("specialist with id {} updated", saveSpecialist.getId());
        return getDtoFromSpecialist(saveSpecialist);


    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> specialist =specialistRepository.findById(id);

        if(specialist.isEmpty() && !specialist.get().getRole().equals(Role.SPECIALIST))
            throw new NotFoundException("specialist with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return getDtoFromSpecialist(specialist.get());

    }

    @Override
    public User findByIdUser(Long id) {
        Optional<User>specialist =specialistRepository.findById(id);
        if (specialist.isEmpty())
            throw new NotFoundException("specialist with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);


        if (!specialist.get().getRole().equals(Role.SPECIALIST))
            throw new NotFoundException("You do not have access to place an order."
                    ,CustomApiExceptionType.BAD_REQUEST);

        return specialist.get();
    }

    @Override
    public void deleteById(Long id) {
        if (specialistRepository.findUserByIdAndRole(id,Role.SPECIALIST).isEmpty())
            throw new NotFoundException("specialist with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);


        specialistRepository.deleteById(id);
        log.info("specialist with id{"+id+"} deleted!");

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
                .role(user.getRole())
                .userImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }
}
