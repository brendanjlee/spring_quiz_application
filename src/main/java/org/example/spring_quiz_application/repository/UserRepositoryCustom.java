package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllUsers();
}
