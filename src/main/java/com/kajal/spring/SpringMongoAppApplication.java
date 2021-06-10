package com.kajal.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.kajal.spring.repository")

@ComponentScan("com.kajal.spring.*")
public class SpringMongoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoAppApplication.class, args);
	}

}
