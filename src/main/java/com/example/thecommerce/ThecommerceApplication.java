package com.example.thecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ThecommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThecommerceApplication.class, args);
    }

}
