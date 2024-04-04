package com.it120p.carehub;

import com.it120p.carehub.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarehubApplication {
	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void setup(){
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(CarehubApplication.class, args);
	}

}
