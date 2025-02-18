package com.dastfs.reposcorer.client.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.dastfs.reposcorer.client.github.GithubApiConstants.SEARCH_URL;

@FeignClient(
        name = "github-api",
        url = "${client.api.github.url}",
        configuration = GithubApiClientConfig.class)
public interface GithubApiClient {

    @GetMapping(SEARCH_URL)
    GithubSearchResponse searchRepos(
            @RequestParam("q") String query,
            @RequestParam("sort") String sort,
            @RequestParam("order") String order,
            @RequestParam("per_page") int perPage,
            @RequestParam("page") int page
    );
}
