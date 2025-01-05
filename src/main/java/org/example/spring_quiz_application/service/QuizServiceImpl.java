package org.example.spring_quiz_application.service;

import org.apache.tomcat.jni.Local;
import org.example.spring_quiz_application.dao.*;
import org.example.spring_quiz_application.domain.Category;
import org.example.spring_quiz_application.domain.Choice;
import org.example.spring_quiz_application.domain.Question;
import org.example.spring_quiz_application.domain.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {
    private final CategoryDAO categoryDAO;
    private final QuizResultDAO quizResultDAO;
    private final QuestionDAO questionDAO;
    private final ChoiceDAO choiceDAO;
    private final QuizQuestionDAO quizQuestionDAO;

    @Autowired
    public QuizServiceImpl(CategoryDAO categoryDAO,
                           QuizResultDAO quizResultDAO
            , QuestionDAO questionDAO
            , ChoiceDAO choiceDAO
            , QuizQuestionDAO quizQuestionDAO) {
        this.categoryDAO = categoryDAO;
        this.quizResultDAO = quizResultDAO;
        this.questionDAO = questionDAO;
        this.choiceDAO = choiceDAO;
        this.quizQuestionDAO = quizQuestionDAO;
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

    @Override
    public List<Question> getAllQuestionsByCategoryId(int categoryId) {
        return questionDAO.getAllQuestionsByCategoryId(categoryId);
    }

    @Override
    public List<Choice> getAllChoicesByQuestionId(int questionId) {
        return choiceDAO.getChoicesByQuestionId(questionId);
    }

    @Override
    public void submitQuiz(int userId, int categoryId, LocalDateTime timeStart,
                           LocalDateTime timeEnd,
                           Map<Integer, Integer> quizAnswers) {
        // create a new quiz result
        int resultId = quizResultDAO.createQuizResult(userId, categoryId,
                timeStart, timeEnd);

        System.out.println("Result Key: " + resultId);

        // record the choices according to quizAnswers
        quizAnswers.forEach((question_id, choice_id) -> {
            quizQuestionDAO.createQuizQuestion(resultId, question_id,
                    choice_id);
        });
    }


}
