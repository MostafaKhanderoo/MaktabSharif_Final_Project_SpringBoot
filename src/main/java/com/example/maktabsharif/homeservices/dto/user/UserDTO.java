package com.example.maktabsharif.homeservices.dto.user;

import com.example.maktabsharif.homeservices.enumeration.Role;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
    private String userImage;
    private Role role;
    private UserStatus userStatus;


}
