package com.example.maktabsharif.homeservices.dto.user;

import org.springframework.web.multipart.MultipartFile;

public record SpecialistCreateDTO(
        String firstname,
        String lastname,
        Long age,
        String username,
        String password,
        String email,
        MultipartFile profileImage
) {
}
