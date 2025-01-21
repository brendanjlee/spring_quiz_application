package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.DTO.UserDTO;
import org.example.spring_quiz_application.model.User;
import org.example.spring_quiz_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserDTO> findAllUsersDTO() {
        try {
            List<User> users = findAllUsers();
            return users.stream().map(UserDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void toggleUserStatus(int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return;
        }
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public void toggleAdminStatus(int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return;
        }
        user.setAdmin(!user.isAdmin());
        userRepository.save(user);
    }
}
