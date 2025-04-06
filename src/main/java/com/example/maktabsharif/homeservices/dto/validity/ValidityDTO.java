package com.example.maktabsharif.homeservices.dto.validity;

import com.example.maktabsharif.homeservices.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidityDTO {
    @NotEmpty
    private String address;
    @NotEmpty
    private Double balance;
    @NotEmpty
    private boolean accountLock;
    @NotEmpty
    private User user;
}
