package com.example.maktabsharif.homeservices.dto.payment;

import com.example.maktabsharif.homeservices.entity.Validity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderDTO {
    private Validity customer;
    private Validity specialist;
    private Double amount;
}
