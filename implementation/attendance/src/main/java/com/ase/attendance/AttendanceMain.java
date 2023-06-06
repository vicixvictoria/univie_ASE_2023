package com.ase.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main class for the Attendance application.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ase.attendance"})
public class AttendanceMain {

    /**
     * The entry point of the Attendance application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(AttendanceMain.class, args);
    }
}