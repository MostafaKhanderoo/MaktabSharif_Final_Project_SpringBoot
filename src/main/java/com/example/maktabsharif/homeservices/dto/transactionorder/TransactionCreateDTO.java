package com.example.maktabsharif.homeservices.dto.transactionorder;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record TransactionCreateDTO(
        String customerValidity,
        String SpecialistValidity,
        @PositiveOrZero
        Double amount,
        @DateTimeFormat
        LocalDateTime timeTransaction
) {
}
