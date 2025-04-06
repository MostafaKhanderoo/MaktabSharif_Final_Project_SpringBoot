package com.example.maktabsharif.homeservices.dto.validity;

import com.example.maktabsharif.homeservices.entity.User;

public record ValidityCreateDTO(
        String address,

        Double balance,

        boolean accountLock,

        User user
) {
}
