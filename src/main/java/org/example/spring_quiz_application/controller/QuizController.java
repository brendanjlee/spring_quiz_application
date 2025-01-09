package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.model.Category;
import org.example.spring_quiz_application.model.Question;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final String basePath = "/api/quiz";
    // autowire constructor

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        Utilities.logApiWithMethod("GET", basePath, "categories");

        // fetch list of category from service and return

        return ResponseEntity.ok(null);
    }

    @PostMapping("start/{userId}")
    public ResponseEntity<String> postStartQuiz(@PathVariable("userId") int userId) {
        Utilities.logApiWithMethod("POST", basePath, "start");

        //

        return ResponseEntity.ok("Quiz started");
    }

    @GetMapping("categories/{categoryId}/questions")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable(
            "categoryId") int categoryId) {
        Utilities.logApiWithMethod("GET", basePath, "categories/questions");
        return ResponseEntity.ok(null);
    }

    @PostMapping("results")
    public ResponseEntity<String> postResults(@RequestBody List<Question> questions) {
        Utilities.logApiWithMethod("POST", basePath, "results",
                String.valueOf(questions.size()));
        return ResponseEntity.ok("Quiz finished");
    }

    @GetMapping("results/{userId}")
    public ResponseEntity<String> getAllResults(@PathVariable("userId") int userId) {
        Utilities.logApiWithMethod("GET", basePath, "results",
                String.valueOf(userId));
        // list of quiz results
        // for home page
        return ResponseEntity.ok("QuizResults");
    }

    @GetMapping("results/{userId}/{quizResultId}")
    public ResponseEntity<String> getResult(@PathVariable("userId") int userId, @PathVariable("quizResultId") int quizResultId) {
        Utilities.logApiWithMethod("GET", basePath, "results",
                String.valueOf(userId),
                String.valueOf(quizResultId));
        //

        return ResponseEntity.ok("QuizResults");
    }
}
