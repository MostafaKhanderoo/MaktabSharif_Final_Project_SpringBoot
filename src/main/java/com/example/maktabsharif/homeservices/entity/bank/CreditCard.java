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

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCard extends BaseEntity<Long> {
    @Column(nullable = false,unique = true)
    private String cardNumber;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String CVV2;
    @Column(nullable = false)
    private LocalDateTime dataCard;
    @ManyToOne
    private User user;
    @ManyToOne
    private Account account;
}
