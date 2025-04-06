package com.example.maktabsharif.homeservices.dto.validity;

import com.example.maktabsharif.homeservices.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ValidityUpdateDTO(
        @NotNull(message = "field id can not be null for update operations")
        Long id,
        @NotEmpty
        String address,
        @NotEmpty
        Double balance,
        @NotEmpty
        boolean accountLock,
        @NotEmpty
        User user
) {
}
