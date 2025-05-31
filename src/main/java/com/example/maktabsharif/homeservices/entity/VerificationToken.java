package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VerificationToken extends BaseEntity<Long> {

    private String token;

    @ManyToOne
    private User user;

    private boolean used = false;
}
