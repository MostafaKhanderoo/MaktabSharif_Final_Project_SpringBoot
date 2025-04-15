package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specialist")
@RequiredArgsConstructor
public class SpecialistController {

    private  final SpecialistService specialistService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createSpecialist(@RequestBody UserCreateDTO createDTO){
        return ResponseEntity
                .ok(specialistService.savaSpecialist(createDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findSpecialistById(@PathVariable Long id){
        return ResponseEntity
                .ok(specialistService.findById(id));
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateSpecialist(@RequestBody UserUpdateDTO updateDTO){
        return ResponseEntity
                .ok(specialistService.updateSpecialist(updateDTO));
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long id){
        specialistService.deleteById(id);
        return ResponseEntity.ok().build();
    }




}
