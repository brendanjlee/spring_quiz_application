package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.DTO.CategoryDTO;
import org.example.spring_quiz_application.DTO.ChoiceDTO;
import org.example.spring_quiz_application.DTO.QuestionDTO;
import org.example.spring_quiz_application.DTO.QuizResultDTO;
import org.example.spring_quiz_application.model.*;
import org.example.spring_quiz_application.repository.CategoryRepository;
import org.example.spring_quiz_application.repository.QuestionRepository;
import org.example.spring_quiz_application.repository.QuizQuestionRepository;
import org.example.spring_quiz_application.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final CategoryRepository categoryRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;

    @Autowired
    public QuizService(CategoryRepository categoryRepository,
                       QuizResultRepository quizResultRepository,
                       QuestionRepository questionRepository,
                       QuizQuestionRepository quizQuestionRepository) {

        this.categoryRepository = categoryRepository;
        this.quizResultRepository = quizResultRepository;
        this.questionRepository = questionRepository;
        this.quizQuestionRepository = quizQuestionRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public List<CategoryDTO> findAllCategoriesDTO() {
        return categoryRepository.findAllCategories().stream()
                .map(CategoryDTO::new).collect(Collectors.toList());
    }

    public List<QuizResult> findQuizResultsByUserId(int userId) {
        return quizResultRepository.findQuizResultsByUserId(userId);
    }

    public QuizResult findQuizResultById(int quizResultId) {
        // map to dto at controller
        return quizResultRepository.findQuizResultById(quizResultId);
    }

    public QuizResultDTO findQuizResultDtoById(int quizResultId) {
        // 1. pull quiz results
        QuizResult quizResult = quizResultRepository.findQuizResultById(quizResultId);

        // 2. pull joined results
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
        quizResultDTO.setResult(result);

        return quizResultDTO;
    }

    public List<Question> findQuestionsByCategoryId(int categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId);
    }

    public List<Choice> findChoicesByQuestionId(int questionId) {
        return quizResultRepository.findChoicesByQuestionId(questionId);
    }

    public List<QuizQuestion> findQuizQuestionsByQuizResultId(int quizResultId) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAll();
        return quizQuestions.stream()
                .filter(quizQuestion -> quizQuestion.getQuizResult().getId() == quizResultId)
                .collect(Collectors.toList());
    }
}

