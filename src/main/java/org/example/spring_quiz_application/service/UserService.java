package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(int id);

    void createUser(String email, String firstName, String lastName,
                    String passwordHash, boolean isAdmin, boolean isActive);

    void updateUser(User user);

    void deleteUser(int id);

    void activateUser(int id);

    void deactivateUser(int id);

    void promoteUser(int id);

    void demoteUser(int id);
}
