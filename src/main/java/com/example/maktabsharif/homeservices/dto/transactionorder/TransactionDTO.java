package com.example.maktabsharif.homeservices.dto.transactionorder;

import jakarta.persistence.Column;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class TransactionDTO {
    private Long id;
    @Column(nullable = false)
    private String customerValidity;
    @Column(nullable = false)
    private String SpecialistValidity;
    @Column(nullable = false)@PositiveOrZero
    private Double amount;
    @Column(nullable = false)
    private LocalDateTime timeTransaction;
}
