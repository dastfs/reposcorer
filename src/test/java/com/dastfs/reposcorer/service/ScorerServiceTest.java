package com.dastfs.reposcorer.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ScorerServiceTest {

    private final ScorerService scorerService = new ScorerService();

    @Test
    void testCountScore_MaxStarsMaxForksRecentUpdate() {
        Float score = scorerService.countScore(200000, 50000, LocalDateTime.now());
        assertEquals(1.0f, score, 0.0001);
    }

    @Test
    void testCountScore_HalfStarsHalfForksModerateRecency() {
        Float score = scorerService.countScore(100000, 25000, LocalDateTime.now().minusMonths(6));
        assertEquals(0.5f, score, 0.1); // Approximate value
    }

    @Test
    void testCountScore_MinStarsMinForksOldUpdate() {
        Float score = scorerService.countScore(0, 0, LocalDateTime.now().minusYears(3));
        assertEquals(0.0f, score, 0.0001);
    }

    @Test
    void testNormalize_BelowMaxValue() {
        double result = scorerService.normalize(50000, 150000);
        assertEquals(0.3333, result, 0.0001);
    }

    @Test
    void testNormalize_AboveMaxValue() {
        double result = scorerService.normalize(200000, 150000);
        assertEquals(1.0, result, 0.0001);
    }

    @Test
    void testCalculateRecencyNorm_RecentUpdate() {
        double result = scorerService.calculateRecencyNorm(LocalDateTime.now());
        assertEquals(1.0, result, 0.0001);
    }

}
