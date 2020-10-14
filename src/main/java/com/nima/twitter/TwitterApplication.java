package com.nima.twitter;

import com.nima.twitter.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class TwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApplication.class, args);
	}

}
