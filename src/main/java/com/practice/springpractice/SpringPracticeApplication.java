package com.practice.springpractice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SpringPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPracticeApplication.class, args);
    }

    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Welcome to Spring Practice!");

            String[] beanNames = ctx.getBeanDefinitionNames();

            Arrays.sort(beanNames);
            for (String beanName: beanNames) {
                System.out.println(beanName);
            }
        };
    }

}
