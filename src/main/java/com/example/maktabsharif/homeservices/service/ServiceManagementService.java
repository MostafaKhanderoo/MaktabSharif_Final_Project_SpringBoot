package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.entity.SubService;

public interface ServiceManagementService {
    void createService(String serviceName);

    void createSubService(String serviceName, String subServiceName, Double price, String description);

    SubService findSubServiceByName(String name);

}
