package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score extends BaseEntity<Long> {

    @OneToOne
    private User specialistId;

    @Column(name = "specialist_score", nullable = false)
    @Min(0)
    @Max(5)
    @PositiveOrZero
    private int specialistScore;

    @Column(length = 400)
    private String description;
    @OneToOne
    private Orders orderId;
}
