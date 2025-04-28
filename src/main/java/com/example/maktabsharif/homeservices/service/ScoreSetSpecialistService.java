package com.example.maktabsharif.homeservices.service;

import com.example.maktabsharif.homeservices.dto.score.ScoreCreateDTO;
import com.example.maktabsharif.homeservices.dto.score.ScoreDto;
import com.example.maktabsharif.homeservices.dto.score.ScoreUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ScoreSetSpecialistService {

    ScoreDto addScoreSpecialist(ScoreCreateDTO scoreCreateDTO);
    ScoreDto updateScore(ScoreUpdateDTO scoreUpdateDTO);
    Optional<ScoreDto> findDtoById(Long id);
    List<ScoreDto> findAllScore();
    void deleteById(Long id);
    Optional<ScoreDto> findBySpecialistId(Long id);
    List<ScoreDto> specialistSetScore(Long specialistId);
    void checkOrderHasScore(Long id);


}
