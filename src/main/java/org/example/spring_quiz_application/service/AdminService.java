package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.dao.*;
import org.example.spring_quiz_application.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final CategoryDAO categoryDAO;
    private final QuizResultDAO quizResultDAO;
    private final QuestionDAO questionDAO;
    private final ChoiceDAO choiceDAO;
    private final QuizQuestionDAO quizQuestionDAO;
    private final UserDAO userDAO;

    @Autowired
    public AdminService(CategoryDAO categoryDAO
            , QuizResultDAO quizResultDAO
            , QuestionDAO questionDAO
            , ChoiceDAO choiceDAO
            , QuizQuestionDAO quizQuestionDAO
            , UserDAO userDAO) {
        this.categoryDAO = categoryDAO;
        this.quizResultDAO = quizResultDAO;
        this.questionDAO = questionDAO;
        this.choiceDAO = choiceDAO;
        this.quizQuestionDAO = quizQuestionDAO;
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
