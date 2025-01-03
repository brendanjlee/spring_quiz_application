package org.example.spring_quiz_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringQuizApplication {

    public static void main(String[] args) {
        System.out.println("Starting Spring Quiz Application...");
        SpringApplication.run(SpringQuizApplication.class, args);
    }

}
