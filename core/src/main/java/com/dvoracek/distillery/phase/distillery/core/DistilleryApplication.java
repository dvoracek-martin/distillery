package com.dvoracek.distillery.phase.distillery.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dvoracek.distillery"})
@EntityScan(basePackages = {"com.dvoracek.distillery.distillation.*"})
@EnableJpaRepositories(basePackages = {"com.dvoracek.distillery.distillation.*"})
@EnableAsync
@EnableScheduling
public class DistilleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistilleryApplication.class, args);
    }
}
