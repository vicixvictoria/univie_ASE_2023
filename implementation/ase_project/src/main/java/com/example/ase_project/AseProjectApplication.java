package com.example.ase_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.ase_project.Event", "com.example.ase_project.EventInventory", "com.example.ase_project.Feedback"})
public class AseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AseProjectApplication.class, args);
    }
}