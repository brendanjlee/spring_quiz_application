package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.dao.UserDAO;
import org.example.spring_quiz_application.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/hello")
    public String sayHello() {
        User user = new User();
        user.setFirstName("Brendan");

        return user.getFirstName();
    }

}
