package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.dto.score.ScoreCreateDTO;
import com.example.maktabsharif.homeservices.dto.score.ScoreDto;
import com.example.maktabsharif.homeservices.dto.score.ScoreUpdateDTO;
import com.example.maktabsharif.homeservices.entity.Orders;
import com.example.maktabsharif.homeservices.entity.Score;
import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.ExistsException;
import com.example.maktabsharif.homeservices.exception.InvalidInputException;
import com.example.maktabsharif.homeservices.repository.ScoreRepository;
import com.example.maktabsharif.homeservices.service.OrderService;
import com.example.maktabsharif.homeservices.service.ScoreSetSpecialistService;
import com.example.maktabsharif.homeservices.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreSetSpecialistService {
    private final ScoreRepository scoreRepository;
    private final SpecialistService specialistService;
    private final OrderService orderService;
    @Override
    public ScoreDto addScoreSpecialist(ScoreCreateDTO scoreCreateDTO) {
        checkOrderHasScore(scoreCreateDTO.orderId());


        Score score = new Score();
        if (scoreCreateDTO.specialistScore() < 0 && scoreCreateDTO.specialistScore() > 5)
            throw new InvalidInputException("score must between 0 - 5",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);


      User specialist=  specialistService.findByIdUser(scoreCreateDTO.specialistId());
        score.setSpecialistScore(scoreCreateDTO.specialistScore());
        score.setSpecialist(specialist);
        score.setDescription(scoreCreateDTO.description());
        Orders order= orderService.findById(scoreCreateDTO.orderId()).get();
        score.setOrder(order);
        scoreRepository.save(score);
        log.info("Score set for specialist with id{" + score.getSpecialist().getFirstname() + "}");
        return getScoreDto(score);
    }

    @Override
    public ScoreDto updateScore(ScoreUpdateDTO scoreUpdateDTO) {
        return null;
    }

    @Override
    public Optional<ScoreDto> findDtoById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ScoreDto> findAllScore() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<ScoreDto> findBySpecialistId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ScoreDto> specialistSetScore(Long specialistId) {
        return null;
    }

    @Override
    public void checkOrderHasScore(Long id) {
        if (scoreRepository.findScoreByOrderId(id).isPresent())
            throw new ExistsException("score has set for order{" + id + "}",
                    CustomApiExceptionType.UNPROCESSIBLE_ENTITY);

    }

    private ScoreDto getScoreDto(Score score) {
        return ScoreDto.builder()
                .id(score.getId())
                .specialistId(score.getSpecialist())
                .specialistScore(score.getSpecialistScore())
                .description(score.getDescription())
                .orderId(score.getOrder())
                .build();


    }
}
