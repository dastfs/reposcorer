package com.dastfs.reposcorer.service;

import com.dastfs.reposcorer.client.github.GithubApiClient;
import com.dastfs.reposcorer.client.github.GithubRepo;
import com.dastfs.reposcorer.client.github.GithubSearchResponse;
import com.dastfs.reposcorer.dto.Repo;
import com.dastfs.reposcorer.dto.RepoScoreResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepoServiceTest {

    @Mock
    private GithubApiClient githubApiClient;

    @Mock
    private ScorerService scorerService;

    @InjectMocks
    private RepoService repositoryService;

    @Test
    void testGetRepositoriesWithScore() {
        String language = "java";
        String createdSince = "2024-01-01";
        int page = 1;
        int size = 10;

        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setName("TestRepo");
        githubRepo.setStars(100);
        githubRepo.setForks(50);

        GithubSearchResponse mockGithubResponse = new GithubSearchResponse();
        mockGithubResponse.setItems(List.of(githubRepo));
        mockGithubResponse.setTotalCount(1);

        Repo repo = new Repo();
        repo.setName("TestRepo");
        repo.setStars(100);
        repo.setForks(50);

        RepoScoreResponse mockRepoScoreResponse = new RepoScoreResponse();
        mockRepoScoreResponse.setRepos(List.of(repo));
        mockRepoScoreResponse.setTotalCount(1);

        when(githubApiClient.searchRepos(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(mockGithubResponse);

        RepoScoreResponse result = repositoryService.getRepositoriesWithScore(language, createdSince, page, size);

        assertEquals(1, result.getTotalCount());
        assertEquals(1, result.getRepos().size());
        assertEquals("TestRepo", result.getRepos().get(0).getName());
        assertEquals(100, result.getRepos().get(0).getStars());
        assertEquals(50, result.getRepos().get(0).getForks());
        verify(scorerService).addScores(mockRepoScoreResponse);
    }

}