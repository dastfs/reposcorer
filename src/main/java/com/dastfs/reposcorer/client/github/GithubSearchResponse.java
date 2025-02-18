package com.dastfs.reposcorer.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GithubSearchResponse {
    @JsonProperty("items")
    private List<GithubRepo> items;
    @JsonProperty("total_count")
    private int totalCount;
}
