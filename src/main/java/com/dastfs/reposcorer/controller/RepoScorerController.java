package com.dastfs.reposcorer.controller;

import com.dastfs.reposcorer.api.RepoScorerApi;
import com.dastfs.reposcorer.dto.RepoScoreResponse;
import com.dastfs.reposcorer.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/repos")
@RequiredArgsConstructor
public class RepoScorerController implements RepoScorerApi {
    private final RepoService scorerService;

    @GetMapping
    public RepoScoreResponse getRepositories(@RequestParam String language,
                                             @RequestParam String createdSince,
                                             @RequestParam int page,
                                             @RequestParam int size) {
        return scorerService.getRepositoriesWithScore(language, createdSince, page, size);
    }

}
