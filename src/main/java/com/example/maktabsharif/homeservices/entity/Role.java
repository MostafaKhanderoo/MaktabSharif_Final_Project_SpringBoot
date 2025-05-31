package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Role extends BaseEntity<Long> {
    public static final String CUSTOMER = "ROLE_CUSTOMER";
    public static final String EXPERT = "ROLE_EXPERT";
    public static final String ADMIN = "ROLE_ADMIN";
    @Column(nullable = false,unique = true)
    private String name;




}
