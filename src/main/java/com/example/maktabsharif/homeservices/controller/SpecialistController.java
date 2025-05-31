    package com.example.maktabsharif.homeservices.controller;

    import com.example.maktabsharif.homeservices.dto.user.SpecialistCreateDTO;
    import com.example.maktabsharif.homeservices.dto.user.UserCreateDTO;
    import com.example.maktabsharif.homeservices.dto.user.UserDTO;
    import com.example.maktabsharif.homeservices.dto.user.UserUpdateDTO;
    import com.example.maktabsharif.homeservices.enumeration.RoleName;
    import com.example.maktabsharif.homeservices.service.SpecialistService;
    import com.example.maktabsharif.homeservices.service.UserService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;

    @RestController
    @RequestMapping("/api/specialist")
    @RequiredArgsConstructor
    public class SpecialistController {

        private  final SpecialistService specialistService;
        private final UserService userService;

        @PostMapping("/create")
        public ResponseEntity<UserDTO> createSpecialist(
                @RequestParam MultipartFile profileImage,
                @RequestParam String firstname,
                @RequestParam String lastname,
                @RequestParam Long age,
                @RequestParam String username,
                @RequestParam String password,
                @RequestParam String email
        ) throws IOException {
            SpecialistCreateDTO createDTO = new SpecialistCreateDTO(firstname,lastname,age,username,password,email,profileImage);
            return ResponseEntity
                    .ok(specialistService.savaSpecialist(createDTO));

        }

        @GetMapping("/{id}")
        public ResponseEntity<UserDTO> findSpecialistById(@PathVariable Long id){
            return ResponseEntity
                    .ok(specialistService.findById(id));
        }


        @PutMapping("/update")
        public ResponseEntity<UserDTO> updateSpecialist(@RequestBody UserUpdateDTO updateDTO) throws IOException {
            return ResponseEntity
                    .ok(specialistService.updateSpecialist(updateDTO));
        }
        @DeleteMapping ("/{id}")
        public ResponseEntity<Void> deleteSpecialist(@PathVariable Long id){
            specialistService.deleteById(id);
            return ResponseEntity.ok().build();
        }




    }
