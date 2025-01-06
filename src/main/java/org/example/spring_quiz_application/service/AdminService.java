package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.DTO.QuizResultPageDTO;
import org.example.spring_quiz_application.dao.*;
import org.example.spring_quiz_application.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final CategoryDAO categoryDAO;
    private final QuizResultDAO quizResultDAO;
    private final QuestionDAO questionDAO;
    private final ChoiceDAO choiceDAO;
    private final QuizQuestionDAO quizQuestionDAO;
    private final UserDAO userDAO;
    private final ContactDAO contactDAO;
    QuizService quizService;

    @Autowired
    public AdminService(CategoryDAO categoryDAO
            , QuizResultDAO quizResultDAO
            , QuestionDAO questionDAO
            , ChoiceDAO choiceDAO
            , QuizQuestionDAO quizQuestionDAO
            , UserDAO userDAO
            , QuizService quizService, ContactDAO contactDAO) {
        this.categoryDAO = categoryDAO;
        this.quizResultDAO = quizResultDAO;
        this.questionDAO = questionDAO;
        this.choiceDAO = choiceDAO;
        this.quizQuestionDAO = quizQuestionDAO;
        this.userDAO = userDAO;
        this.quizService = quizService;
        this.contactDAO = contactDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<QuizResultPageDTO> getAllQuizResults() {
        List<QuizResultPageDTO> res = new ArrayList<QuizResultPageDTO>();

        // query all results
        List<QuizResult> quizResults = quizResultDAO.getAllQuizResults();
        List<Category> categories = categoryDAO.getAllCategories();

        for (QuizResult quizResult : quizResults) {
            // set category names
            quizResult.setCategoryName(quizService.getCategoryNameById(quizResult.getCategoryId()));

            // get questions for category
            List<Question> questions =
                    quizService.getAllQuestionsByCategoryId(quizResult.getCategoryId());
            quizService.mapChoicesToQuestions(questions);

            // map answer & calculate results
            questions.forEach(question -> {
                question.getChoices().forEach(choice -> {
                    quizService.mapQuizQuestionsToChoices(choice,
                            quizResult.getId());
                });
            });
            int result = 0;
            for (Question question : questions) {
                for (Choice choice : question.getChoices()) {
                    if (choice.isAnswer() && choice.isUserAnswer()) result++;
                }
            }

            // get username
            User user = userDAO.getUserById(quizResult.getUserId());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" +
                    "-MM-dd HH:mm:ss");
            String formattedDate = quizResult.getTimeStart().format(formatter);

            QuizResultPageDTO curr = new QuizResultPageDTO();
            curr.setStartTime(formattedDate);
            curr.setCategoryName(quizResult.getCategoryName());
            curr.setNumQuestions(questions.size());
            curr.setScore(result);
            curr.setQuizResultId(quizResult.getId());
            curr.setUserId(user.getId());
            curr.setUserFullName(user.getFirstName() + " " + user.getLastName());
            res.add(curr);
        }
        res.sort(Comparator.comparing(QuizResultPageDTO::getStartTime).reversed());
        return res;
    }

    public List<QuizResultPageDTO> getFilteredQuizResults(List<QuizResultPageDTO> results, Integer categoryId, Integer userId) {
        return results.stream()
                .filter(result -> {
                    boolean matchesCat =
                            categoryId == null || result.getCategoryName().equals(quizService.getCategoryNameById(categoryId));
                    boolean matchesUser =
                            userId == null || result.getUserId() == userId;
                    return matchesCat && matchesUser;
                })
                .collect(Collectors.toList());
    }

    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}
