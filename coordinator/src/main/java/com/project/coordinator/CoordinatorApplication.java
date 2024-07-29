package com.project.coordinator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoordinatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoordinatorApplication.class, args);
	}

}