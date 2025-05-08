package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
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

    @Column(nullable = false)
    @Lob
    private byte[] userImage;
    @Enumerated(EnumType.STRING)
    @ManyToOne
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
