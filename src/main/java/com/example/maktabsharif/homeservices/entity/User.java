package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User extends BaseEntity<Long> implements UserDetails {
    private String firstname;
    private String lastname;
    private Long age;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    private String password;
    private String email;
    private LocalDateTime registerDate;


    @Lob

    private byte[] userImage;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> role = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
