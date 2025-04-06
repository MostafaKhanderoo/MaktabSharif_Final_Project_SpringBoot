package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {
}