package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createCustomer(@RequestBody UserCreateDTO createDTO){
        return ResponseEntity
                .ok(customerService.savaCustomer(createDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity
                .ok(customerService.findByIdAndRole(id));

    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateCustomer(@RequestBody UserUpdateDTO updateDTO){
        return ResponseEntity
                .ok(customerService.updateCustomer(updateDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteById(id);
        return ResponseEntity.ok().build();

    }



}
