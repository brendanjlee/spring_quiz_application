package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.domain.Category;
import org.example.spring_quiz_application.domain.QuizResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuizService {
    String getCategoryNameById(int id);

    List<Category> getAllCategories();

    List<QuizResult> getAllQuizResultsByUserId(int userId);
}
