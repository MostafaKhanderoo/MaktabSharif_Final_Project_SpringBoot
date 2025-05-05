package com.example.maktabsharif.homeservices.controller;

import com.example.maktabsharif.homeservices.dto.score.ScoreCreateDTO;
import com.example.maktabsharif.homeservices.dto.score.ScoreDto;
import com.example.maktabsharif.homeservices.service.ScoreSetSpecialistService;
import com.example.maktabsharif.homeservices.service.impl.ScoreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreServiceImpl scoreService;
    @PostMapping("/set/score")
    public ResponseEntity<ScoreDto> addScoreForSpecialist(@RequestBody ScoreCreateDTO scoreCreateDTO){
        return ResponseEntity.ok(scoreService.addScoreSpecialist(scoreCreateDTO));
    }

}
