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
import com.example.maktabsharif.homeservices.repository.RoleRepository;
import com.example.maktabsharif.homeservices.repository.UserRepository;
import com.example.maktabsharif.homeservices.service.UserService;
import com.example.maktabsharif.homeservices.service.ValidityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidityService validityService;
    private final RoleRepository roleRepository;



    @Override
    public UserDTO saveUser(UserCreateDTO createDTO, RoleName roleName) throws IOException {

        User user = new User();


        user.setFirstname(createDTO.firstname());
        user.setLastname(createDTO.lastname());
        if (createDTO.age() >=18){
            user.setAge(createDTO.age());
        }else {
            throw new InvalidInputException("Age must be 18 or older!"
                    , CustomApiExceptionType.UNPROCESSIBLE_ENTITY);
        }

        user.setUsername(createDTO.username());
        user.setPassword(passwordEncoder.encode(createDTO.password()));
        user.setEmail(createDTO.email());
        user.setRegisterDate(LocalDateTime.now());
        Role role = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new NotFoundException("Role not found",CustomApiExceptionType.NOT_FOUND));
        user.setRole(role);
        user.setUserStatus(UserStatus.ACTIVE);


        user.setUserImage(createDTO.profileImage().getBytes());

        var saveUser =userRepository.save(user);
        validityService.createValidityForUser(user);
        log.info(roleName+" with id {} saved", saveUser.getId());
        return getDtoFromUser(saveUser);
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO updateDTO) throws IOException {
        return null;
    }

    @Override
    public UserDTO findByIdAndRole(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<User> chooseLoginUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public User findUserByFirstName(String firstName) {
        return null;
    }

    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {

    }

    @Override
    public List<UserDTO> searchUserByAge(Long age, Operator operator) {
        return null;
    }

    @Override
    public List<UserDTO> searchUserByFieldAndValue(String field, String value) {
        return null;
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=   userRepository.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getUserStatus() == UserStatus.PENDING,
                true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().name()))

        );
    }
    private UserDTO getDtoFromUser(User user) {
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
