package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.model.User;
import org.example.spring_quiz_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        // check password
        if (user.getPasswordHash().equals(password)) {
            return user;
        }
        return null;
    }

    @Transactional
    public boolean registerUser(String email, String firstName, String lastName,
                                String password) {
        if (userRepository.findUserByEmail(email) != null) {
            return false;
        }

        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .passwordHash(password).build();

        try {
            userRepository.createUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
