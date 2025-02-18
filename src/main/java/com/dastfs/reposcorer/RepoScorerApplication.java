package com.dastfs.reposcorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RepoScorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepoScorerApplication.class, args);
    }

}
