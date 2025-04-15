package com.example.maktabsharif.homeservices.entity.bank;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account extends BaseEntity<Long> {

    @Column(nullable = false, unique = true)
    private String address;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private boolean accountLock;
    @ManyToOne
    private User user;

    @ManyToOne
    private Bank bank;

}
