package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.servicedto.ServicesDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.SubServiceCreateDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.SubServiceDTO;
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
    public ServicesDTO createService(String serviceName) {
        if (serviceRepository.findByName(serviceName).isPresent())
            throw new ExistsException("service with name " +serviceName+ " alReady exists!",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

        ServiceEntity serviceEntity = new ServiceEntity(serviceName);
        serviceRepository.save(serviceEntity);
        log.info("service with name{"+serviceName+"} added");
        return getDtoFromService(serviceEntity);
    }


    @Override
    public SubServiceDTO createSubService(SubServiceCreateDTO createDTO) {
        var service=  serviceRepository.findByName(createDTO.service().getName())
                .orElseThrow(()->new  IllegalArgumentException("service not found!"));

        if (subServiceRepository.existsByNameAndService(createDTO.name(),service))
            throw new ExistsException(createDTO.name()+" subService alReady Exists for "+service.getName(),
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


        SubService subService = new SubService(createDTO.name(), createDTO.basePrice(), createDTO.description());
        service.addSubService(subService);
        subServiceRepository.save(subService);
        log.info("subService with name{"+createDTO.name()+"} added");
        return getDtoFromSubService(subService);
    }

    @Override
    public SubService findSubServiceByName(String name) {
        Optional<SubService> service =subServiceRepository.findByName(name);
        if (service.isEmpty())
            throw new NotFoundException("Service "+name+" not exists",CustomApiExceptionType.NOT_FOUND);
        return service.get();

    }

    @Override
    public SubService findSubServiceById(Long id) {
        Optional<SubService> service =subServiceRepository.findById(id);
        if (service.isEmpty())
            throw new NotFoundException("Service "+id+" not exists",CustomApiExceptionType.NOT_FOUND);
        return service.get();
    }

    private ServicesDTO getDtoFromService (ServiceEntity service){
        return ServicesDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .subServices(service.getSubServices())
                .build();
    }

    private SubServiceDTO getDtoFromSubService(SubService subService){
        return SubServiceDTO.builder()
                .id(subService.getId())
                .name(subService.getName())
                .service(subService.getService())
                .basePrice(subService.getBasePrice())
                .description(subService.getDescription())
                .build();
    }
}