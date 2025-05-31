package com.example.maktabsharif.homeservices.dto.user;

import lombok.Builder;


@Builder
public record UserCreateDTO(
        String firstname,
        String lastname,
        Long age,
        String username,
        String password,
        String email
) {
}

