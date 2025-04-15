package com.example.maktabsharif.homeservices.dto.user;

import com.example.maktabsharif.homeservices.enumeration.Role;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record UserCreateDTO(
        String firstname,
        String lastname,
        Long age,
        String username,
        String password,
        String email,
        LocalDateTime registerDate,
        String userImage,
        Role role,
        UserStatus userStatus

) {
}

