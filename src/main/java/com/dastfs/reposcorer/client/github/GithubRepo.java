package com.dastfs.reposcorer.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GithubRepo {
    private String name;
    @JsonProperty("stargazers_count")
    private Integer stars;
    @JsonProperty("forks_count")
    private Integer forks;
    @JsonProperty("pushed_at")
    private LocalDateTime lastCommitDate;
}
