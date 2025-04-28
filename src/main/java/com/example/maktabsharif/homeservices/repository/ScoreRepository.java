package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.dto.score.ScoreDto;
import com.example.maktabsharif.homeservices.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {
    @Query("SELECT c from Score c where c.orderId.id =:id")
    Optional<ScoreDto> findScoreByOrderId(@Param("id") Long id);
}