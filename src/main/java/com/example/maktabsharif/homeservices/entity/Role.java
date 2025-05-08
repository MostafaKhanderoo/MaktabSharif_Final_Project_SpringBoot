package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import com.example.maktabsharif.homeservices.enumeration.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Role extends BaseEntity<Long> {

    @Column
    private RoleName roleName;




}
