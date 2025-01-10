package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.model.User;
import org.example.spring_quiz_application.service.AuthService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final String baseUrl = "/api/auth";

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> postLogin(@RequestBody Map<String, String> body) {
        Utilities.logApiWithMethod("POST", baseUrl, "login");

        // authenticate
        User possibleUser = authService.authenticateUser(body.get("email"),
                body.get("password"));

        if (possibleUser != null) {
            return ResponseEntity.ok(possibleUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(@RequestBody Map<String,
            String> body) {
        Utilities.logApiWithMethod("POST", baseUrl, "register");

        // register with service
        System.out.println(body.toString());

        boolean success = authService.registerUser(body.get("email"),
                body.get("firstName"), body.get("lastName"), body.get(
                        "password"));

        if (success) {
            return ResponseEntity.ok("User registered successfully");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                "registration failed");
    }
}
