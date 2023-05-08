package com.ase.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ase.attendance"})
public class AttendanceMain {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceMain.class, args);
    }
}