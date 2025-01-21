package org.example.spring_quiz_application;

import org.example.spring_quiz_application.DAO.ContactDao;
import org.example.spring_quiz_application.DAO.QuizResultDao;
import org.example.spring_quiz_application.model.QuizResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringQuizApplication {

    public static void main(String[] args) {
        System.out.println("Starting Spring Quiz Application...");
        SpringApplication.run(SpringQuizApplication.class, args);
    }

}
