package com.cs.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.cs.event"})
public class NewSpringBootApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NewSpringBootApplication.class, args);
	}

}
