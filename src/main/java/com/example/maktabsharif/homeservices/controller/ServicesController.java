package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.servicedto.ServicesDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.SubServiceCreateDTO;
import com.example.maktabsharif.homeservices.dto.servicedto.SubServiceDTO;
import com.example.maktabsharif.homeservices.entity.ServiceEntity;
import com.example.maktabsharif.homeservices.entity.SubService;
import com.example.maktabsharif.homeservices.service.ServiceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ServicesController {
    private final ServiceManagementService managementService;

    @GetMapping("/allservices")
    public ResponseEntity<List<ServiceEntity>> getServices(){
        return ResponseEntity
                .ok(managementService.findAllService());
    }

    @PostMapping("/add/subservice")
    public ResponseEntity<SubServiceDTO> createSubService(@RequestBody SubServiceCreateDTO createDTO){
        return ResponseEntity
                .ok(managementService.createSubService(createDTO));
    }
    @PostMapping("/add/service")
    public ResponseEntity<ServicesDTO> createService(@RequestParam String title){
        return ResponseEntity
                .ok(managementService.createService(title));
    }
    @GetMapping ("/subService/{serviceName}")
    private ResponseEntity<List<SubService>> findAllSubServiceByServiceName(@PathVariable String serviceName){
       return ResponseEntity.ok(managementService.findAllSubServiceByServiceName(serviceName));
    }

}
