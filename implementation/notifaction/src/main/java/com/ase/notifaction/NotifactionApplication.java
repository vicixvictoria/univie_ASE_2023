package com.ase.notifaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotifactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifactionApplication.class, args);
	}

}
