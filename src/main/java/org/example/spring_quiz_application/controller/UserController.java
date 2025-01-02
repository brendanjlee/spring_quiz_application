package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.User;
import org.example.spring_quiz_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getPasswordHash(), user.isAdmin(),
                user.isActive());
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}/activate")
    public void activateUser(@PathVariable int id) {
        userService.activateUser(id);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivateUser(@PathVariable int id) {
        userService.deactivateUser(id);
    }

    @PatchMapping("/{id}/promote")
    public void promoteUser(@PathVariable int id) {
        userService.promoteUser(id);
    }

    @PatchMapping("/{id}/demote")
    public void demoteUser(@PathVariable int id) {
        userService.demoteUser(id);
    }
}
