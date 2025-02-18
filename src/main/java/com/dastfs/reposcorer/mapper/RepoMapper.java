package com.dastfs.reposcorer.mapper;

import com.dastfs.reposcorer.client.github.GithubRepo;
import com.dastfs.reposcorer.client.github.GithubSearchResponse;
import com.dastfs.reposcorer.dto.Repo;
import com.dastfs.reposcorer.dto.RepoScoreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RepoMapper {
    RepoMapper INSTANCE = Mappers.getMapper(RepoMapper.class);

    @Mapping(source = "items", target = "repos")
    @Mapping(source = "totalCount", target = "totalCount")
    RepoScoreResponse toRepoScoreResponse(GithubSearchResponse response);

    List<Repo> toRepoList(List<GithubRepo> githubRepos);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "stars", target = "stars")
    @Mapping(source = "forks", target = "forks")
    @Mapping(source = "lastCommitDate", target = "lastUpdated")
    @Mapping(target = "score", ignore = true)
    Repo toRepo(GithubRepo githubRepo);
}
