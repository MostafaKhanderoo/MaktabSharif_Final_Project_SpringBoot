package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.servicedto.*;
import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import com.example.maktabsharif.homeservices.entity.SubService;

import java.util.List;
import java.util.Optional;

public interface ServiceManagementService {
    ServicesDTO createService(String serviceName);

    SubServiceDTO createSubService(SubServiceCreateDTO createDTO);

    Optional<SubService> findSubServiceByName(String name);
    Optional<SubService> findSubServiceById(Long id);
    List<ServiceEntity> findAllService();

    List<SubService> findAllSubServiceByServiceName(String serviceName);

}
