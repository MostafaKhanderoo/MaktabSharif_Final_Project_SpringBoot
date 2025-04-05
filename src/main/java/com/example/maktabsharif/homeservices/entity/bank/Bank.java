package com.example.maktabsharif.homeservices.entity.bank;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Bank extends BaseEntity<Long> {
    private String name;

    private String branch;
}
