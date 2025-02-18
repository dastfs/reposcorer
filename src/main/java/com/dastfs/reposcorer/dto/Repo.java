package com.dastfs.reposcorer.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.function.Function;

@Data
public class Repo {
    private String name;
    private Integer stars;
    private Integer forks;
    private LocalDateTime lastUpdated;
    private Float score;

    public void setScore(Function<Repo, Float> scoringFunction) {
        this.score = scoringFunction.apply(this);
    }
}
