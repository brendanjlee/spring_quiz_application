package org.example.spring_quiz_application.controller;

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
            // quiz result
            // question
            // choices
            QuizResult quizResult =
                    quizService.findQuizResultById(quizResultId);

            // test new filter
            QuizResultDTO quizResultDTO222 = new QuizResultDTO(quizResult);
//            List<QuestionDTO> = quizResult.getId(
            List<QuizQuestion> quizQuestions222 = quizResult.getQuizQuestions();
            List<Question> questions222 = quizQuestions222.stream()
                    .map(QuizQuestion::getQuestion).collect(Collectors.toList());
            List<Choice> userchoice222 = quizQuestions222.stream()
                    .map(QuizQuestion::getUserChoice).collect(Collectors.toList());

            // i just have to build that up now into 222 DTO
            // 1. choice dto
            List<ChoiceDTO> userChoiceDTO222 = userchoice222.stream()
                    .map(ChoiceDTO::new).collect(Collectors.toList());
            // 2. questionDTO, add choice
            List<QuestionDTO> questionDTOs222 = questions222.stream()
                    .map(QuestionDTO::new).collect(Collectors.toList());
            questionDTOs222.forEach(questionDTO -> {
                questionDTO.getChoices().forEach(choice -> {
                    int choiceId = choice.getId();
                    choice.setUserAnswer(userChoiceDTO222.stream().map(ChoiceDTO::getId).collect(Collectors.toList()).contains(choiceId));
                });
            });


            ///  test test

            List<Question> questions =
                    quizService.findQuestionsByCategoryId(quizResult.getCategory().getId());

            // map to dto
            // must filter out the 5 questions that the user got form
            // quiz_question
            // todo need to add category name manually
            QuizResultDTO quizResultDTO = new QuizResultDTO(quizResult);
//            quizResultDTO.setCategoryName(quizResult.getCategory().getName());

            List<QuestionDTO> questionDTOS = questions.stream()
                    .map(QuestionDTO::new).collect(Collectors.toList());

            for (QuestionDTO questionDTO : questionDTOS) {
                List<Choice> choices =
                        quizService.findChoicesByQuestionId(questionDTO.getId());
                List<ChoiceDTO> choiceDTOS =
                        choices.stream().map(ChoiceDTO::new).collect(Collectors.toList());
                questionDTO.setChoices(choiceDTOS);
            }

            // quizquestion
            List<QuizQuestion> quizQuestions =
                    quizService.findQuizQuestionsByQuizResultId(quizResultId);
            // extract question id
            Set<Integer> questionIds = quizQuestions.stream()
                    .map(quizQuestion -> quizQuestion.getQuestion().getId()).collect(Collectors.toSet());

            // filter dto by matching id
            List<QuestionDTO> filteredQuestionDTOs = questionDTOS.stream()
                    .filter(questionDTO -> questionIds.contains(questionDTO.getId()))
                    .collect(Collectors.toList());

            for (QuestionDTO questionDTO : questionDTOS) {
                System.out.println(questionDTO.getId());
                if (questionIds.contains(questionDTO.getId())) {
                    filteredQuestionDTOs.add(questionDTO);
                }
            }

            quizResultDTO.setQuestions(questionDTOs222); // set only
            // quiz questions

            System.out.println("quizResultDTO: " + quizResultDTO);
            return ResponseEntity.ok(quizResultDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
