package com.meganote.springone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class SpringOneApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringOneApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.run(args);
	}

}
