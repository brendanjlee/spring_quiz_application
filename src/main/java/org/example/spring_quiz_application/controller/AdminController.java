package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.model.Category;
import org.example.spring_quiz_application.model.Contact;
import org.example.spring_quiz_application.model.Question;
import org.example.spring_quiz_application.model.User;
import org.example.spring_quiz_application.service.ContactService;
import org.example.spring_quiz_application.service.UserService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ContactService contactService;
    private final String basePath = "/api/admin";
    private final UserService userService;

    @Autowired
    public AdminController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    /* USER Management */
    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers() {
        Utilities.logApiWithMethod("GET", basePath, "users");
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/users/{userId}/toggleActive")
    public ResponseEntity<User> toggleUserActiveStatus(@PathVariable("userId") int userId) {
        Utilities.logApiWithMethod("PATCH", basePath, "users/{}/status" +
                userId);
        try {
            userService.toggleUserStatus(userId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/users/{userId}/toggleAdmin")
    public ResponseEntity<User> toggleUserAdminStatus(@PathVariable("userId") int userId) {
        Utilities.logApiWithMethod("PATCH", basePath, "users/{}/promote" + userId);
        try {
            userService.toggleAdminStatus(userId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok().body(null);
    }

    /* Quiz Result Management */
    @GetMapping("quiz/categories")
    public ResponseEntity<List<Category>> getCategoriesAdmin() {
        Utilities.logApiWithMethod("GET", basePath, "quiz/categories");

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("quiz/results")
    public ResponseEntity<String> getQuizResults() {
        Utilities.logApiWithMethod("GET", basePath, "quiz/results");
        return ResponseEntity.ok("quizResultManagement");
    }

    @GetMapping("quiz/results/user/{userId}")
    public ResponseEntity<String> getQuizResultsByUserId(@PathVariable(
            "userId") String userId) {
        Utilities.logApiWithMethod("GET", basePath, "quiz/results/user" +
                "/{userId}");
        return ResponseEntity.ok("quizResultManagement");
    }

    @GetMapping("quiz/results/user/category/{categoryId}")
    public ResponseEntity<String> getQuizResultsByCategoryId(@PathVariable(
            "categoryId") int categoryId) {
        Utilities.logApiWithMethod("GET", basePath, "quiz/results/category" +
                        "/{categoryId}",
                String.valueOf(categoryId));
        return ResponseEntity.ok("quizResultManagement");
    }

    @GetMapping("results/user/{userId}/category/{categoryId}")
    public ResponseEntity<String> getQuizResultsByCategoryIdAndUserId(@PathVariable("userId") int userId, @PathVariable("categoryId") int categoryId) {
        Utilities.logApiWithMethod("GET", basePath, "quiz/results/",
                String.valueOf(userId),
                String.valueOf(categoryId));
        return ResponseEntity.ok("quizResultManagement");
    }

    /* Question Management */
    @GetMapping("questions")
    public ResponseEntity<List<Question>> getQuestions() {
        Utilities.logApiWithMethod("GET", basePath, "questions");
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("questions/{questionId}")
    public ResponseEntity<Question> getQuestion(@PathVariable("questionId") int questionId) {
        Utilities.logApiWithMethod("GET", basePath, "questions/{}",
                String.valueOf(questionId));
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("questions")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Utilities.logApiWithMethod("POST", basePath, "questions");
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("questions/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable("questionId") int questionId,
                                                   @RequestBody Question question) {
        Utilities.logApiWithMethod("PUT", basePath, "questions/{}",
                String.valueOf(questionId));
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("questions/{questionId}")
    public ResponseEntity<Question> toggleQuestion(@PathVariable("questionId") int questionId) {
        Utilities.logApiWithMethod("PATCH", basePath, "questions/{}",
                String.valueOf(questionId));
        return ResponseEntity.ok().body(null);
    }

    /* Contact Management */
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        Utilities.logApiWithMethod("GET", basePath, "contacts");
        try {
            List<Contact> contacts = contactService.findAllContacts();
            return ResponseEntity.ok().body(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/contacts")
    public ResponseEntity<String> addContact(@RequestBody Map<String, String> body) {
        Utilities.logApiWithMethod("POST", basePath, "contacts");

        // create new with service
        contactService.submitContact(body.get("email"), body.get("subject"),
                body.get("message"), LocalDateTime.now());

        return ResponseEntity.ok().body(null);
    }
}
