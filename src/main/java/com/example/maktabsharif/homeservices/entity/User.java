package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import com.example.maktabsharif.homeservices.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @Column(nullable = false)
    @Lob
    private byte[] userImage;

    @ManyToOne(fetch = FetchType.EAGER) // تغییر مهم: اضافه کردن FetchType.EAGER
    @JoinColumn(nullable = false) // مشخص کردن نام ستون
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null || this.role.getRoleName() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + this.role.getRoleName().name())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus != null && userStatus == UserStatus.ACTIVE;    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
