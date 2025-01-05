package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.domain.Category;
import org.example.spring_quiz_application.domain.Choice;
import org.example.spring_quiz_application.domain.Question;
import org.example.spring_quiz_application.domain.QuizResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public interface QuizService {
    String getCategoryNameById(int id);

    List<Category> getAllCategories();

    List<QuizResult> getAllQuizResultsByUserId(int userId);

    List<Question> getAllQuestionsByCategoryId(int categoryId);

    List<Choice> getAllChoicesByQuestionId(int questionId);

    void submitQuiz(int userId, int categoryId, LocalDateTime timeStart,
                    LocalDateTime timeEnd, Map<Integer, Integer> quizAnswers);
}
