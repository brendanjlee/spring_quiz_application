package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.dao.CategoryDAO;
import org.example.spring_quiz_application.dao.QuizResultDAO;
import org.example.spring_quiz_application.domain.Category;
import org.example.spring_quiz_application.domain.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private final CategoryDAO categoryDAO;
    private final QuizResultDAO quizResultDAO;

    @Autowired
    public QuizServiceImpl(CategoryDAO categoryDAO,
                           QuizResultDAO quizResultDAO) {
        this.categoryDAO = categoryDAO;
        this.quizResultDAO = quizResultDAO;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public String getCategoryNameById(int id) {
        Category c = categoryDAO.getCategoryById(id);
        return c.getName();
    }

    @Override
    public List<QuizResult> getAllQuizResultsByUserId(int userId) {
        List<QuizResult> quizResults =
                quizResultDAO.getQuizResultsByUserId(userId);

        quizResults.forEach(quizResult -> {
            quizResult.setCategoryName(getCategoryNameById(quizResult.getId()));
        });

        return quizResults;
    }
}
