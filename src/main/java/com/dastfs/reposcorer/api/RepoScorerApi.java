package com.dastfs.reposcorer.api;

import com.dastfs.reposcorer.dto.RepoScoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Repository Scorer API", description = "API to fetch repositories with scoring")
public interface RepoScorerApi {

    @Operation(summary = "Get scored repositories", description = "Fetch repositories based on language, creation date, and other filters with calculated scores")
    @ApiResponse(responseCode = "200", description = "Repositories fetched successfully")
    RepoScoreResponse getRepositories(
            @Parameter(description = "Programming language") @RequestParam String language,
            @Parameter(description = "Repositories created since this date (YYYY-MM-DD)") @RequestParam String createdSince,
            @Parameter(description = "Page number") @RequestParam int page,
            @Parameter(description = "Number of results per page") @RequestParam int size
    );
}
