package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.servicedto.*;
import com.example.maktabsharif.homeservices.entity.SubService;

public interface ServiceManagementService {
    ServicesDTO createService(String serviceName);

    SubServiceDTO createSubService(SubServiceCreateDTO createDTO);

    SubService findSubServiceByName(String name);
    SubService findSubServiceById(Long id);

}
