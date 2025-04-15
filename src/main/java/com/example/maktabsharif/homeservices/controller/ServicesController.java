package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.servicedto.ServiceCreateDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.ServicesDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.SubServiceCreateDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.SubServiceDTO;
import com.example.maktabsharif.homeservices.service.ServiceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
@RequiredArgsConstructor
public class ServicesController {
    private final ServiceManagementService managementService;

    @PostMapping("create/subservice")
    public ResponseEntity<SubServiceDTO> createSubService(@RequestBody SubServiceCreateDTO createDTO){
        return ResponseEntity
                .ok(managementService.createSubService(createDTO));
    }
    @PostMapping("create/service")
    public ResponseEntity<ServicesDTO> createService(@RequestBody String name){
        return ResponseEntity
                .ok(managementService.createService(name));
    }

}
