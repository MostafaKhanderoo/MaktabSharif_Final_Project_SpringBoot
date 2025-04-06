package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import com.example.maktabsharif.homeservices.entity.SubService;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.ServiceRepository;
import com.example.maktabsharif.homeservices.repository.SubServiceRepository;
import com.example.maktabsharif.homeservices.service.ServiceManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ServiceManagementImpl implements ServiceManagementService {

    private final ServiceRepository serviceRepository;

    private final SubServiceRepository subServiceRepository;

    @Override
    public void createService(String serviceName) {
        if (serviceRepository.findByName(serviceName).isPresent())
            throw new ExistsException("service with name " +serviceName+ " alReady exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        ServiceEntity serviceEntity = new ServiceEntity(serviceName);
        serviceRepository.save(serviceEntity);

    }


    @Override
    public void createSubService(String serviceName, String subServiceName, Double price, String description) {
        var service=  serviceRepository.findByName(serviceName)
                .orElseThrow(()->new  IllegalArgumentException("service not found!"));

        if (subServiceRepository.existsByNameAndService(subServiceName,service))
            throw new ExistsException(subServiceName+" subService alReady Exists for "+service.getName(),
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


        SubService subService = new SubService(subServiceName, price, description);
        service.addSubService(subService);
        subServiceRepository.save(subService);
    }

    @Override
    public SubService findSubServiceByName(String name) {
        Optional<SubService> service =subServiceRepository.findByName(name);
        if (service.isEmpty())
            throw new NotFoundException("Service "+name+" not exists",CustomApiExceptionType.NOT_FOUND);
        return service.get();

    }
}