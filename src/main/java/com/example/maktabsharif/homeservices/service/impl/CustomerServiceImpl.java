package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.Operator;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.UserRepository;
import com.example.maktabsharif.homeservices.service.CustomerService;
import com.example.maktabsharif.homeservices.service.RoleService;
import com.example.maktabsharif.homeservices.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Primary
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final ValidityServiceImpl validityService;
    private final RoleService roleService;


    @Override
    public UserDTO savaCustomer(UserCreateDTO createDTO) throws IOException {
//        if (userRepository.existsUserByUsernameAndRole(createDTO.username(), RoleName.CUSTOMER))
//            throw new ExistsException("Customer with username {"
//                    + createDTO.username() + "} already exists!",
//                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


        User user = new User();
        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        user.setUsername(createDTO.username());
        user.setPassword(createDTO.password());
        user.setAge(createDTO.age());
        user.setEmail(createDTO.email());
        user.setRegisterDate(LocalDateTime.now());
        Role role =roleService.findByName(Role.CUSTOMER);
        user.getRole().add(role);
        user.setUserStatus(UserStatus.PENDING);

        var saveCustomer =userService.saveUser(user);
        validityService.createValidityForUser(user);
        log.info("Customer with id {} saved", saveCustomer.getId());
        return getDtoFromCustomer(saveCustomer);
    }

    @Override
    public UserDTO updateCustomer(UserUpdateDTO updateDTO) throws IOException {
        userService.findById(updateDTO.id());

        User user = userService.chooseLoginUserById(updateDTO.id());

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

            User savedCustomer =userService.updateUser(user);
        log.info("Customer with id {} updated", savedCustomer.getId());
        return getDtoFromCustomer(savedCustomer);

    }

    @Override
    public UserDTO findById(Long id) {
        User customer =userService.findById(id);

        return getDtoFromCustomer(customer);
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findUserByIdAndRole(id, RoleName.CUSTOMER).isEmpty())
            throw new NotFoundException("Customer with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);


        validityService.deleteValidityByUserId(id);
        userRepository.deleteById(id);
        log.info("customer with id{"+id+"} deleted!");

    }

    @Override
    public Optional<User> chooseLoginCustomerById(Long id) {
        var customerLog =userRepository.findUserByIdAndRole(id, RoleName.CUSTOMER);
        if (customerLog.isEmpty())
            throw new NotFoundException("Customer with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return  customerLog;
    }



    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
      User user=  userService.findById(userId);

      if (!user.getPassword().equals(currentPassword))
          throw new  InvalidInputException("Current password is wrong!"
                  ,CustomApiExceptionType.BAD_REQUEST);

      if (newPassword.length() <8)
          throw new IllegalArgumentException("Password must be at least 8 characters");

        user.setPassword(newPassword);
        log.info("user id "+ userId+" password changed");
        userRepository.save(user);


    }

    @Override
    public List<UserDTO> searchUserByAge(Long age, Operator operator) {
        Specification<User> specification = UserSpecification.filterByLong(age,operator);
        List<User> users = userRepository.findAll(specification);
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getAge(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRegisterDate(),
                        user.getUserStatus(),
                        user.getUserImage()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> searchUserByFieldAndValue(String field, String value) {

       Specification<User> specification= UserSpecification.filterByString(field,value);
        List<User> users = userRepository.findAll(specification);
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getAge(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRegisterDate(),

                        user.getUserStatus(),
                        user.getUserImage()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> customers = userRepository.findAllByRole(RoleName.CUSTOMER);
        return customers.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getAge(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRegisterDate(),

                        user.getUserStatus(),
                        user.getUserImage().clone()))
                .collect(Collectors.toList());
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
                .profileImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }

}
