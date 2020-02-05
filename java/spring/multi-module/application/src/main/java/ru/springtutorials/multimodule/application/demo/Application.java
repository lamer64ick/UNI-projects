package ru.springtutorials.multimodule.application.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.springtutorials.multimodule.service.demo.MyService;

@SpringBootApplication(scanBasePackages = "ru.springtutorials.multimodule")
@RestController
public class Application {

    private final MyService myService;

    public Application(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/")
    public String home() {
        return myService.message();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}