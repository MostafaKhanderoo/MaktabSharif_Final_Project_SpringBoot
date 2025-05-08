package com.example.maktabsharif.homeservices.dto.user;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponseDTO(String firstName,
                              String lastName,
                              Long age,
                              String username,
                              String password,
                              String email,
                              LocalDateTime registerDate,
                              Role role,
                              UserStatus userStatus,
                              byte[] profileImage
) {

    public  static UserResponseDTO from(User user){
        return  new UserResponseDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getAge(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRegisterDate(),
                user.getRole(),
                user.getUserStatus(),
                user.getUserImage()
        );
    }
}
