package com.StayNest.StayNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StayNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayNestApplication.class, args);
	}

}
