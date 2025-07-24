package com.plantcommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlantCommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlantCommunityApplication.class, args);
    }
}