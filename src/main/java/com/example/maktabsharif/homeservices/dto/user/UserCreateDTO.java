package com.example.maktabsharif.homeservices.dto.user;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;


@Builder
public record UserCreateDTO(
        String firstname,
        String lastname,
        Long age,
        String username,
        String password,
        String email,
        MultipartFile profileImage
) {
}

