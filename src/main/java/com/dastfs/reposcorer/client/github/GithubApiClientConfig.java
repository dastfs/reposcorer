package com.dastfs.reposcorer.client.github;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class GithubApiClientConfig {

    @Value("${client.api.github.version}")
    public String version;
    @Value("${client.api.github.token}")
    private String githubToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", "application/vnd.github+json");
            requestTemplate.header("Authorization", "Bearer " + githubToken);
            requestTemplate.header("X-GitHub-Api-Version", version);

            requestTemplate.uri(requestTemplate.request().url()
                    .replaceAll("%2B", "+")
            );
        };
    }

    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

}
