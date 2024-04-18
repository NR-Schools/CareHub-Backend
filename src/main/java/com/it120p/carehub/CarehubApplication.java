package com.it120p.carehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarehubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarehubApplication.class, args);
	}

}
