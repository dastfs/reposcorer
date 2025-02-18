package com.dastfs.reposcorer.service;

import com.dastfs.reposcorer.dto.Repo;
import com.dastfs.reposcorer.dto.RepoScoreResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ScorerService {
    private static final int RECENCY_DECAY_CONSTANT = 12; // Recency decay constant (12 months = 1-year half-life)
    private static final int MAX_STARS = 200_000; // Define reasonable caps for normalization
    private static final int MAX_FORKS = 50_000;

    /**
     * Calculates the popularity score of a repository based on stars, forks, and recency of the last update.
     * <p>
     * The score is computed using a geometric mean-like formula, inspired by the Human Development Index (HDI):
     * <ul>
     *     <li>Stars and forks are capped and normalized.</li>
     *     <li>Recency is measured as months since the last commit, decaying exponentially.</li>
     * </ul>
     * This ensures that a repository's popularity is balanced across stars, forks, and activity.
     *
     * @param stars          The number of stars the repository has.
     * @param forks          The number of forks the repository has.
     * @param lastCommitDate The date of the last commit.
     * @return A float representing the popularity score (0 to 1).
     */
    public Float countScore(int stars, int forks, LocalDateTime lastCommitDate) {
        double starsNorm = normalize(stars, MAX_STARS);
        double forksNorm = normalize(forks, MAX_FORKS);
        double recencyNorm = calculateRecencyNorm(lastCommitDate);

        // HDI-like geometric mean
        double score = Math.cbrt(starsNorm * forksNorm * recencyNorm);

        return (float) score;
    }

    double normalize(int value, int maxValue) {
        return (double) Math.min(value, maxValue) / maxValue;
    }

    double calculateRecencyNorm(LocalDateTime lastCommitDate) {
        long monthsSinceLastUpdate = ChronoUnit.MONTHS.between(lastCommitDate.toLocalDate(), LocalDateTime.now().toLocalDate());
        return Math.exp(-((double) monthsSinceLastUpdate / RECENCY_DECAY_CONSTANT));
    }

    private Float scoreFunctionForRepo(Repo repo) {
        return countScore(repo.getStars(), repo.getForks(), repo.getLastUpdated());
    }

    /**
     * Adds scores to each repository in the response.
     *
     * @param repoScoreResponse the response containing the repositories
     */
    public void addScores(RepoScoreResponse repoScoreResponse) {
        repoScoreResponse.getRepos().forEach(repo ->
                repo.setScore(this::scoreFunctionForRepo)
        );
    }
}
