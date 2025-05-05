package com.example.maktabsharif.homeservices.dto.transactionorder;

import jakarta.validation.constraints.PositiveOrZero;

public record TransactionCreateDTO(
        Long orderSelected,
        String customerValidity,
        String specialistValidity,
        @PositiveOrZero
        Double amount

) {
}
