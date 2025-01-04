package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @PostMapping("/login")
    public boolean postLogin(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        System.out.println("Login Requested");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        return true;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        System.out.println("register");
        return true;
    }
}
