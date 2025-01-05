package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.domain.User;

import java.util.Optional;

public interface AuthService {
    public Optional<User> validateLogin(String email, String passwordHash);
}
