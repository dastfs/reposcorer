package com.dastfs.reposcorer.dto;

import lombok.Data;

import java.util.List;

@Data
public class RepoScoreResponse {
    private List<Repo> repos;
    private int totalCount;
    private int page;
    private int size;
}
