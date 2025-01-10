package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.model.Category;
import org.example.spring_quiz_application.model.QuizResult;
import org.example.spring_quiz_application.repository.CategoryRepository;
import org.example.spring_quiz_application.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final CategoryRepository categoryRepository;
    private final QuizResultRepository quizResultRepository;

    @Autowired
    public QuizService(CategoryRepository categoryRepository,
                       QuizResultRepository quizResultRepository) {
        this.categoryRepository = categoryRepository;
        this.quizResultRepository = quizResultRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public List<QuizResult> findQuizResultsByUserId(int userId) {
        return quizResultRepository.findQuizResultsByUserId(userId);
    }
}

