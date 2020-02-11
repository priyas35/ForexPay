package com.squad.forexpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForexPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForexPayApplication.class, args);
	}

}
