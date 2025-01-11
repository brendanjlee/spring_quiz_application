package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DTO.CategoryDTO;
import org.example.spring_quiz_application.DTO.ChoiceDTO;
import org.example.spring_quiz_application.DTO.QuestionDTO;
import org.example.spring_quiz_application.DTO.QuizResultDTO;
import org.example.spring_quiz_application.model.*;
import org.example.spring_quiz_application.service.QuizService;
import org.example.spring_quiz_application.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        List<Category> categories = quizService.findAllCategories();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(CategoryDTO::new).collect(Collectors.toList());
        System.out.println("categoryDTOS: " + categoryDTOS);

        return ResponseEntity.ok(categoryDTOS);
    }

    @PostMapping("start/{userId}")
    public ResponseEntity<String> postStartQuiz(@PathVariable("userId") int userId) {
        Utilities.logApiWithMethod("POST", basePath, "start");

        //todo also take in category id

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
    @GetMapping("results/user/{userId}")
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
    public ResponseEntity<QuizResultDTO> getResult(@PathVariable("userId") int userId, @PathVariable("quizResultId") int quizResultId) {
        Utilities.logApiWithMethod("GET", basePath, "results",
                String.valueOf(userId),
                String.valueOf(quizResultId));
        try {
            // 1. pull quiz results from service
            QuizResult quizResult =
                    quizService.findQuizResultById(quizResultId);

            // 2. pull joined tables from quizResult
            List<QuizQuestion> quizQuestionList = quizResult.getQuizQuestions();
            List<Question> questionList = quizQuestionList.stream()
                    .map(QuizQuestion::getQuestion).collect(Collectors.toList());
            List<Choice> userChoiceList = quizQuestionList.stream()
                    .map(QuizQuestion::getUserChoice).collect(Collectors.toList());

            // 3. create DTO out of the joined tables
            List<ChoiceDTO> userChoiceDTOs = userChoiceList.stream()
                    .map(ChoiceDTO::new).collect(Collectors.toList());
            List<QuestionDTO> questionDTOs = questionList.stream()
                    .map(QuestionDTO::new).collect(Collectors.toList());
            questionDTOs.forEach(questionDTO -> {
                questionDTO.getChoices().forEach(choice -> {
                    int choiceId = choice.getId();
                    choice.setUserAnswer(userChoiceDTOs.stream().map(ChoiceDTO::getId).collect(Collectors.toList()).contains(choiceId));
                });
            });

            // 4. attach questions to DTO
            QuizResultDTO quizResultDTO = new QuizResultDTO(quizResult);
            quizResultDTO.setQuestions(questionDTOs);

            // 5. calculate result
            int result = 0;
            for (QuestionDTO questionDTO : quizResultDTO.getQuestions()) {
                for (ChoiceDTO choiceDTO : questionDTO.getChoices()) {
                    if (choiceDTO.isAnswer() && choiceDTO.isUserAnswer()) {
                        result++;
                    }
                }
            }
            System.out.println("result: " + result);
            quizResultDTO.setResult(result);


            return ResponseEntity.ok(quizResultDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
