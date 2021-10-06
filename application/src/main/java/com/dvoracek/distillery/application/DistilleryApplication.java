package com.dvoracek.distillery.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dvoracek.distillery.*"})
@EntityScan(basePackages = {"com.dvoracek.distillery.domain.*"})
@EnableJpaRepositories(basePackages = "com.dvoracek.distillery.domain.*")
public class DistilleryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistilleryApplication.class, args);
    }
}
