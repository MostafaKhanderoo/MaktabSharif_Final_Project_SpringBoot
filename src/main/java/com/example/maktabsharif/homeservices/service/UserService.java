package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);
}
