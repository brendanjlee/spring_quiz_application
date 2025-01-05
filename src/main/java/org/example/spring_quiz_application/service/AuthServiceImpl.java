package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.dao.UserDAO;
import org.example.spring_quiz_application.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;

    @Autowired
    public AuthServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> validateLogin(String email, String passwordHash) {
        Optional<User> user =
                Optional.ofNullable(userDAO.getUserByEmail(email));

        if (user.isPresent()) {
            if (passwordHash.equals(user.get().getPasswordHash())) {
                return user;
            }
        }

        return Optional.empty();
    }
}
