package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.gwing"})
public class NewSpringBootApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NewSpringBootApplication.class, args);
	}

}
