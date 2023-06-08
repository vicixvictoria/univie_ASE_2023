package com.ase.recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main class for the Recommender application.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ase.recommender"})
public class RecommenderMain {

    /**
     * The entry point of the Recommender application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(RecommenderMain.class, args);
    }
}