package com.example.maktabsharif.homeservices.dto.servicedto;

import com.example.maktabsharif.homeservices.entity.SubService;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class ServicesDTO {
    Long id;

    private  String name;

    private List<SubService> subServices ;
}
