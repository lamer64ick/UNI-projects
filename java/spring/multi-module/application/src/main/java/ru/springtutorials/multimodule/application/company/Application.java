package ru.springtutorials.multimodule.config.company;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(scanBasePackages = "ru.springtutorials.multimodule")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        Company company = ctx.getBean("company", Company.class);
        System.out.println(company.getAddress().getStreet() + " " + company.getAddress().getNumber());
    }
}
