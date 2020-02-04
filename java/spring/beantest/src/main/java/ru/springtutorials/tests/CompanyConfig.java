package ru.springtutorials.tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackageClasses = Company.class)
public class CompanyConfig {
    @Bean
    public Address getAddress() {
        return new Address("1st Lane", 23);
    }
}
