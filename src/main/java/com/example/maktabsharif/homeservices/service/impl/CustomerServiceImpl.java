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
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.RoleRepository;
import com.example.maktabsharif.homeservices.repository.UserRepository;
import com.example.maktabsharif.homeservices.service.CustomerService;
import com.example.maktabsharif.homeservices.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final ValidityServiceImpl validityService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO savaCustomer(UserCreateDTO createDTO) throws IOException {
//        if (userRepository.existsUserByUsernameAndRole(createDTO.username(), RoleName.CUSTOMER))
//            throw new ExistsException("Customer with username {"
//                    + createDTO.username() + "} already exists!",
//                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


        User user = new User();

        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        if (createDTO.age() >=18){
            user.setAge(createDTO.age());
        }else {
            throw new InvalidInputException("Age must be 18 or older!"
                    , CustomApiExceptionType.UNPROCESSIBLE_ENTITY);
        }
        user.setAge(createDTO.age());
        user.setUsername(createDTO.username());
        user.setPassword(passwordEncoder.encode(createDTO.password()));
        user.setEmail(createDTO.email());
        user.setRegisterDate(LocalDateTime.now());
        Role role =roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(()->new NotFoundException(CustomApiExceptionType.NOT_FOUND));
        user.setRole(role);
        user.setUserStatus(UserStatus.PENDING);


        user.setUserImage(createDTO.profileImage().getBytes());

        var saveCustomer =userRepository.save(user);
        validityService.createValidityForUser(user);
        log.info("Customer with id {} saved", saveCustomer.getId());
        return getDtoFromCustomer(saveCustomer);
    }

    @Override
    public UserDTO updateCustomer(UserUpdateDTO updateDTO) throws IOException {
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

        if (userRepository.existsUserByUsernameAndRole(updateDTO.username(), RoleName.CUSTOMER))
            throw new ExistsException("Customer with username {"
                    + updateDTO.username() + "} already exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        updateCustomer.setUsername(updateDTO.username());
        updateCustomer.setEmail(updateDTO.email());
        updateCustomer.setPassword(updateDTO.password());
        updateCustomer.setUserImage(updateDTO.image().getBytes());

        User savedCustomer =userRepository.save(updateCustomer);
        log.info("Customer with id {} updated", savedCustomer.getId());
        return getDtoFromCustomer(savedCustomer);

    }

    @Override
    public UserDTO findByIdAndRole(Long id) {
        Optional<User> customer =userRepository.findUserByIdAndRole(id, RoleName.CUSTOMER);
        if (customer.isEmpty())
            throw new NotFoundException("Customer with id{"+
                    id+"} not found!"
                    ,CustomApiExceptionType.NOT_FOUND);

        return getDtoFromCustomer(customer.get());
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
    public User findUserByFirstName(String firstName) {
        return   userRepository.findUserByRoleAndFirstname(RoleName.CUSTOMER,firstName);
    }

    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
      User user=  userRepository.findUserById(userId)
                .orElseThrow(()-> new NotFoundException("Customer not found"
                        ,CustomApiExceptionType.NOT_FOUND));

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
                        user.getRole(),
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
                        user.getRole(),
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
                        user.getRole(),
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
                .role(user.getRole())
                .profileImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }

}
