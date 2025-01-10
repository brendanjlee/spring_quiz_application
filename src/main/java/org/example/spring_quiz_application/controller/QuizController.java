package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DTO.QuizResultDTO;
import org.example.spring_quiz_application.model.Category;
import org.example.spring_quiz_application.model.Question;
import org.example.spring_quiz_application.model.QuizResult;
import org.example.spring_quiz_application.service.QuizService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;
    private final String basePath = "/api/quiz";

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        Utilities.logApiWithMethod("GET", basePath, "categories");

        // fetch list of category from service and return

        List<Category> categories = quizService.findAllCategories();
        System.out.println("categories: " + categories);

        return ResponseEntity.ok(categories);
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

    // get results by user id
    @GetMapping("results/{userId}")
    public ResponseEntity<List<QuizResultDTO>> getAllResults(@PathVariable(
            "userId") int userId) {
        Utilities.logApiWithMethod("GET", basePath, "results",
                String.valueOf(userId));
        try {
            List<QuizResult> quizResults =
                    quizService.findQuizResultsByUserId(userId);
            List<QuizResultDTO> response = quizResults.stream()
                    .map(QuizResultDTO::new).collect(Collectors.toList());

            System.out.println("response: " + response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
