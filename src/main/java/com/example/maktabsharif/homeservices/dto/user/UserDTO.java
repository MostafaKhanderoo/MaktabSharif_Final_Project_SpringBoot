package com.example.maktabsharif.homeservices.dto.user;

import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private Long age;
    private String username;
    private String password;
    private String email;
    private LocalDateTime registerDate;
    private Role role;
    private UserStatus userStatus;
    private byte[] profileImage;


}
