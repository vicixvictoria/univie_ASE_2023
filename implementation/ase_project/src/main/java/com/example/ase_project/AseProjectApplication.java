package com.example.ase_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.ase_project.event", "com.example.ase_project.eventInventory", "com.example.ase_project.Feedback", "com.example.ase_project.searchServiceEvents", "com.example.ase_project.taggingEvent","com.example.ase_project.bookmarkEvent", "com.example.ase_project.login"})
public class AseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AseProjectApplication.class, args);
    }
}