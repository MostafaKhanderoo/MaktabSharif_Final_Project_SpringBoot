package com.example.maktabsharif.homeservices.dto.user;

import com.example.maktabsharif.homeservices.enumeration.Role;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record UserUpdateDTO(
        @NotNull(message = "field id can not be null for update operations")
        @PositiveOrZero
        Long id,
        String firstname,
        String lastname,
        Long age,
        String username,
        String password,
        String email,
        LocalDateTime registerDate,
        String image,
        Role role,
        UserStatus userStatus

) {


}