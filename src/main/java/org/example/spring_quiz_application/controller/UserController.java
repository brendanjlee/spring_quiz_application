package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.model.User;
import org.example.spring_quiz_application.service.UserService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final String basePath = "/api/users";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        Utilities.logApi(basePath, "");
        List<User> users = userService.findAllUsers();
        return users == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") int userId) {
        Utilities.logApi(basePath, "", String.valueOf(userId));
        //        User user = userService.find
        return ResponseEntity.ok(null);
    }
}
