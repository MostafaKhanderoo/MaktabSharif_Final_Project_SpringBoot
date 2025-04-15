package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createManager(@ModelAttribute UserCreateDTO createDTO){
        return ResponseEntity
                .ok(managerService.savaManager(createDTO));
    }

    @GetMapping
    public ResponseEntity<UserDTO> getManagerById(@RequestParam(name = "id") Long id){
        return ResponseEntity
                .ok(managerService.findById(id));

    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<UserDTO> deleteManager(@PathVariable Long id){
//
//    }




}
