package org.example.spring_quiz_application.service;

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

    public List<QuizResult> findQuizResultsByUserId(int userId) {
        return quizResultRepository.findQuizResultsByUserId(userId);
    }

    public QuizResult findQuizResultById(int quizResultId) {
        // map to dto at controller
        return quizResultRepository.findQuizResultById(quizResultId);
    }

    public List<Question> findQuestionsByCategoryId(int categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId);
    }

    public List<Choice> findChoicesByQuestionId(int questionId) {
        return quizResultRepository.findChoicesByQuestionId(questionId);
    }

    public List<QuizQuestion> findQuizQuestionsByQuizResultId(int quizResultId) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAll();
        List<QuizQuestion> filetered = quizQuestions.stream()
                .filter(quizQuestion -> quizQuestion.getQuizResult().getId() == quizResultId)
                .collect(Collectors.toList());
        return filetered;
    }
}

