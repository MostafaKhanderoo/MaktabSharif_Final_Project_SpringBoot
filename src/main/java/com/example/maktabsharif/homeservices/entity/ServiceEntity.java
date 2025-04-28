package com.example.maktabsharif.homeservices.entity;

import com.example.maktabsharif.homeservices.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "service")
@NoArgsConstructor
public class ServiceEntity extends BaseEntity<Long> {
    @Column(nullable = false,unique = true)
    private  String name;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SubService> subServices ;

    public ServiceEntity(String name) {
        this.name = name;
    }
    public void addSubService(SubService subService) {
        subServices.add(subService);
        subService.setService(this);
    }

}
