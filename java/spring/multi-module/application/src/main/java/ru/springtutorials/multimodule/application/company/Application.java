package ru.springtutorials.multimodule.application.company;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.springtutorials.multimodule.config.company.Company;
import ru.springtutorials.multimodule.config.company.Config;

@SpringBootApplication(scanBasePackages = "ru.springtutorials.multimodule.config")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        Company company = ctx.getBean("company", Company.class);
        System.out.println(company.getAddress().getStreet() + " " + company.getAddress().getNumber());
    }
}
