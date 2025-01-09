package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.util.Utilities;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String baseUrl = "/api/auth";
    // autowire constructor with service
    // auth_service

    @PostMapping("/login")
    public ResponseEntity<String> postLogin(@RequestParam("email") String email,
                                            @RequestParam("password") String password) {
        Utilities.logApiWithMethod("POST", baseUrl, "login");

        // authenticate

        return ResponseEntity.ok("login");
    }

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(@RequestParam("email") String email,
                                               @RequestParam("first_name") String firstName,
                                               @RequestParam("last_name") String last_name,
                                               Model model) {
        Utilities.logApiWithMethod("POST", baseUrl, "register");

        // register

        return ResponseEntity.ok("register");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> postLogout(HttpServletRequest request,
                                             Model model) {
        Utilities.logApiWithMethod("POST", baseUrl, "logout");

        // logout

        return ResponseEntity.ok("logout");
    }
}
