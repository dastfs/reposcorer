package com.dastfs.reposcorer.service;

import com.dastfs.reposcorer.client.github.GithubApiClient;
import com.dastfs.reposcorer.client.github.GithubSearchResponse;
import com.dastfs.reposcorer.dto.RepoScoreResponse;
import com.dastfs.reposcorer.mapper.RepoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepoService {
    private final GithubApiClient githubApiClient;
    private final ScorerService scorerService;
    private final RepoMapper repoMapper = RepoMapper.INSTANCE;

    public RepoScoreResponse getRepositoriesWithScore(String language, String createdSince, int page, int size) {
        String query = "language:" + language + "+created:>" + createdSince;
        GithubSearchResponse githubSearchResponse = githubApiClient.searchRepos(query, "stars", "desc", size,  page);
        RepoScoreResponse repoScoreResponse = repoMapper.toRepoScoreResponse(githubSearchResponse);
        scorerService.addScores(repoScoreResponse);
        return repoScoreResponse;
    }


}
