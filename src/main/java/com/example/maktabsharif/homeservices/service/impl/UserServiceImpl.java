package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.Operator;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.UserRepository;
import com.example.maktabsharif.homeservices.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public User saveUser(User user) throws IOException {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new ExistsException(user.getUsername()+ "already exist"
                    ,CustomApiExceptionType.UNAUTHORIZED);
        if(userRepository.existsByEmail(user.getEmail()))
            throw new ExistsException(user.getEmail()+ "already exist"
                    ,CustomApiExceptionType.UNAUTHORIZED);
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws IOException {
        if (StringUtils.hasText(user.getFirstname()))
            user.setFirstname(user.getFirstname());
        if (StringUtils.hasText(user.getLastname()))
            user.setLastname(user.getLastname());
        if (userRepository.existsByUsername(user.getUsername()))
            throw new ExistsException(user.getUsername()+" already exist"
                    ,CustomApiExceptionType.UNAUTHORIZED);
        if (user.getAge() <18)
            throw new IllegalArgumentException("user must be older than 18");
        if(userRepository.existsByEmail(user.getEmail()))
            throw new ExistsException(user.getEmail()+ " already exist"
                    ,CustomApiExceptionType.UNAUTHORIZED);


        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }




    @Override
    public User findById(Long id) {
     return userRepository.findById(id)
             .orElseThrow(()->new  NotFoundException("User with id {"+id+"} not found!",
                     CustomApiExceptionType.NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
        log.info("User with id {"+id+"} deleted!");
    }

    @Override
    public User chooseLoginUserById(Long id) {
        return  userRepository.findById(id)
                .orElseThrow(()->new  NotFoundException("user with this id "+id+" not found!"
                        ,CustomApiExceptionType.NOT_FOUND));
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
                        user.getUserStatus(),
                        user.getUserImage().clone()))
                .collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // ساخت UserDetails با اطلاعات کامل کاربر
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
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
                .profileImage(user.getUserImage())
                .userStatus(user.getUserStatus())
                .build();
    }
}
