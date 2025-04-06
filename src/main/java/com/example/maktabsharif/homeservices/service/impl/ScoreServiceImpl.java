package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.repository.ScoreRepository;
import com.example.maktabsharif.homeservices.service.ScoreSetSpecialistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreSetSpecialistService {
    private  final ScoreRepository scoreRepository;
}
