package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Validity extends BaseEntity<Long> {

    @Column(nullable = false, unique = true)
    private String address;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private boolean accountLock;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

}
