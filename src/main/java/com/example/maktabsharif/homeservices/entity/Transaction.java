 package com.example.maktabsharif.homeservices.entity;

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
public class Transaction extends BaseEntity<Long> {
    @Column(nullable = false)
    private String customerValidity;
    @Column(nullable = false)
    private String SpecialistValidity;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private LocalDateTime timeTransaction;
    @ManyToOne
    private Orders orders;

}
