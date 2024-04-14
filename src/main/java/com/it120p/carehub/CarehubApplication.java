package com.it120p.carehub;

import com.it120p.carehub.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CarehubApplication extends SpringBootServletInitializer{
	@Autowired
	private TwilioConfig twilioConfig;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CarehubApplication.class);
	}

	@PostConstruct
	public void setup(){
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(CarehubApplication.class, args);
	}

}
