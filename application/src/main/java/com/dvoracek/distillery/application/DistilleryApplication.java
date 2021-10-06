package com.dvoracek.distillery.application;

import com.dvoracek.distillery.domain.user.User;
import com.dvoracek.distillery.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.stream.Stream;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dvoracek.distillery.*"})
@ComponentScan(basePackages = {"com.dvoracek.distillery.*"})
@ComponentScan(basePackages = {"com.dvoracek.distillery.service.*"})
@ComponentScan(basePackages = {"com.dvoracek.distillery.web"})
@ComponentScan(basePackages = {"com.dvoracek.distillery.domain.*"})
@EntityScan(basePackages = {"com.dvoracek.distillery.domain.*"})
@EnableJpaRepositories(basePackages = "com.dvoracek.distillery.domain.*")
public class DistilleryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistilleryApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
        };
    }
}
