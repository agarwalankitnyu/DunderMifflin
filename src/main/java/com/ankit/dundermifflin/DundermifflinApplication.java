package com.ankit.dundermifflin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.ankit.dundermifflin.persistence.repo") 
@EntityScan("com.ankit.dundermifflin.persistence.model")
@SpringBootApplication
public class DundermifflinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DundermifflinApplication.class, args);
		System.out.println("\"You miss 100% of the shots you don't take. - Wayne Gretzky\"");
		System.out.println("                                            - Michael Scott");
	}

}
