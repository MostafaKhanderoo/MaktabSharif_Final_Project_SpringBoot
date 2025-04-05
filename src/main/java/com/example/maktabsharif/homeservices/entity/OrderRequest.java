package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "order_request")
public class OrderRequest extends BaseEntity<Long> {
    @ManyToOne
    private User SpecialistAcceptRequest;

    @ManyToOne
    private Orders order;

    @Column(name = "specialist_suggestion")
    private Double  SpecialistSuggestion;
}
