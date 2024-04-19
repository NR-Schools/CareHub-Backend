package com.it120p.carehub;

import com.it120p.carehub.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class CarehubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarehubApplication.class, args);
	}

}
