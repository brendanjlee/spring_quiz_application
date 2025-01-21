package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DAO.ContactDao;
import org.example.spring_quiz_application.DTO.*;
import org.example.spring_quiz_application.model.*;
import org.example.spring_quiz_application.service.QuizService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    public ResponseEntity<List<CategoryDTO>> getCategoriesDTO() {
        Utilities.logApiWithMethod("GET", basePath, "categories");

        List<CategoryDTO> categoryDTOS = quizService.findAllCategoriesDTO();
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("categories/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryDTO(@PathVariable(
            "categoryId") int categoryId) {
        Utilities.logApiWithMethod("GET", basePath, "categories/{}",
                String.valueOf(categoryId));
        CategoryDTO categoryDTO = quizService.findCategoryDtoById(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    @PostMapping("submit/{userId}")
    public ResponseEntity<Integer> postStartQuiz(@PathVariable("userId") int userId,
                                                 @RequestBody QuizResultSubmitDTO quizResultSubmitDTO) {
        Utilities.logApiWithMethod("POST", basePath, "submit");


        try {
            int quizResultId = quizService.submitQuizResult(userId,
                    quizResultSubmitDTO);

            return ResponseEntity.ok(quizResultId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("categories/{categoryId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCategoryId(@PathVariable(
            "categoryId") int categoryId) {
        Utilities.logApiWithMethod("GET", basePath, "categories/questions");
        List<QuestionDTO> questionDTOS =
                quizService.findQuestionDTOsByCategoryId(categoryId);
        return ResponseEntity.ok(questionDTOS);
    }

    /* Quiz Results */

    @GetMapping("results")
    public ResponseEntity<List<QuizResultDTO>> getAllResults() {
        Utilities.logApiWithMethod("GET", basePath, "results");
        try {
            List<QuizResultDTO> quizResultDTOS =
                    quizService.findAllQuizResultDTO();

            // map questions

            return ResponseEntity.ok(quizResultDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // get results by user id
    @GetMapping("results/user/{userId}")
    public ResponseEntity<List<QuizResultDTO>> getAllResults(@PathVariable(
            "userId") int userId) {
        Utilities.logApiWithMethod("GET", basePath, "results",
                String.valueOf(userId));
        try {
            // todo move mapping to service
            List<QuizResult> quizResults =
                    quizService.findQuizResultsByUserId(userId);
            List<QuizResultDTO> response = quizResults.stream()
                    .map(QuizResultDTO::new)
                    .sorted(Comparator.comparing(QuizResultDTO::getTimeStart).reversed()).collect(Collectors.toList());

            System.out.println("response: " + response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("results/{userId}/{quizResultId}")
    public ResponseEntity<QuizResultDTO> getResult(@PathVariable("userId") int userId,
                                                   @PathVariable(
                                                           "quizResultId") int quizResultId) {
        Utilities.logApiWithMethod("GET", basePath, "results",
                String.valueOf(userId),
                String.valueOf(quizResultId));
        try {
            QuizResultDTO quizResultDTO =
                    quizService.findQuizResultDtoById(quizResultId);
            return ResponseEntity.ok(quizResultDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/hibernateTest")
    public ResponseEntity<String> getHibernateTest() {
        Utilities.logApiWithMethod("GET", basePath, "hibernateTest");
        try {
            ContactDao contactDao = new ContactDao();
            Contact contact = contactDao.getContactById(1);
            System.out.println(contact);
            return ResponseEntity.ok("Hibernate Test");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
