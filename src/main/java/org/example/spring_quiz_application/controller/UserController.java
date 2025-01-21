package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DTO.UserDTO;
import org.example.spring_quiz_application.model.User;
import org.example.spring_quiz_application.service.UserService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        Utilities.logApi(basePath, "");

        List<UserDTO> usersDTO = userService.findAllUsersDTO();

        return usersDTO == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") int userId) {
        Utilities.logApi(basePath, "", String.valueOf(userId));
        User user = userService.findUserById(userId);
        return user == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> req) {
        Utilities.logApi(basePath, "", req.toString());
        req.forEach((key, value) -> {
            System.out.println(value);
        });

        return ResponseEntity.ok("Created new user");
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok("Deleted user with id " + userId);
    }

    @PatchMapping("{userId}/status")
    public ResponseEntity<String> updateUserStatus(@PathVariable("userId") int userId, @RequestParam boolean activate) {
        if (activate) {
            return ResponseEntity.ok("User with id " + userId + " is " +
                    "activated");
        }
        return ResponseEntity.ok("User with id " + userId + " is deactivated");
    }
}
