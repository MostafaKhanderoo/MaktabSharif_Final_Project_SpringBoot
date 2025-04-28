package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
import com.example.maktabsharif.homeservices.dto.user.UserDTO;
import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
import com.example.maktabsharif.homeservices.enumeration.Operator;
import com.example.maktabsharif.homeservices.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createCustomer(
            @RequestParam MultipartFile profileImage,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam Long age,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email
    ) throws IOException {
        UserCreateDTO createDTO = new UserCreateDTO(firstname, lastname, age, username, password, email, profileImage);
        createDTO.firstname();
        return ResponseEntity
                .ok(customerService.savaCustomer(createDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok(customerService.findByIdAndRole(id));

    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateCustomer(@RequestBody UserUpdateDTO updateDTO) throws IOException {
        return ResponseEntity
                .ok(customerService.updateCustomer(updateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/search/value")
    public ResponseEntity<List<UserDTO>> searchByFieldAndValue(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value) {
        List<UserDTO> users = customerService.searchUserByFieldAndValue(field, value);
        return ResponseEntity.ok(users);
    }


    @GetMapping("/search/age")
    public ResponseEntity<List<UserDTO>> searchByAge(
            @RequestParam(required = false) Long age,
            @RequestParam(required = false)Operator operator
            ){
        List<UserDTO>users =customerService.searchUserByAge(age,operator);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/all/customers")
    public ResponseEntity<List<UserDTO>> getAllCustomers(){
       return ResponseEntity.ok(customerService.getAllUser());
    }

}
