package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.*;
import org.example.spring_quiz_application.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final UserDAO userDAO;
    private final ContactDAO contactDAO;
    private final QuestionDAO questionDAO;
    private final ChoiceDAO choiceDAO;

    public AdminController(UserDAO userDAO, ContactDAO contactDAO,
                           QuestionDAO questionDAO, ChoiceDAO choiceDAO) {
        this.userDAO = userDAO;
        this.contactDAO = contactDAO;
        this.questionDAO = questionDAO;
        this.choiceDAO = choiceDAO;
    }

    @PostMapping("toggleActiveStatus")
    public String toggleActiveStatus(@RequestParam("userId") int userId,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") == null) {
            System.out.println("No active user in session");
            return "redirect:/";
        }
        User currrentUser = (User) session.getAttribute("user");
        if (!currrentUser.isAdmin()) {
            System.out.println("Current user is not admin");
            return "redirect:/";
        }

        // get the request user and update
        User user = userDAO.getUserById(userId);
        if (user.isActive()) {
            userDAO.deactivateUser(userId);
        } else {
            userDAO.activateUser(userId);
        }

        return "redirect:/userManagement";
    }

    @PostMapping("submitContact")
    public String submitContact(@RequestParam("email") String email,
                                @RequestParam("subject") String subject,
                                @RequestParam("message") String message,
                                Model model) {
        contactDAO.createContact(subject, message, email);
        return "redirect:/contact";
    }

    @PostMapping("toggleActiveStatus/question")
    public String toggleQuestionActiveStatus(@RequestParam("questionId") int questionId) {
        Question question = questionDAO.getQuestion(questionId);
        if (question.isActive()) {
            questionDAO.disableQuestion(questionId);
        } else {
            questionDAO.enableQuestion(questionId);
        }

        return "redirect:/questionManagement";
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(@RequestParam("questionId") int questionId,
                               @RequestParam("text") String text,
                               @RequestParam Map<String, String> choices) {
        // process the choices
        List<Choice> choiceList = new ArrayList<>();
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            if (entry.getKey().startsWith("choices")) {
                String[] parts = entry.getKey().split("\\.");
                String choiceIdString = parts[0];
                String[] choiceParts = choiceIdString.split("[\\[\\]]");
                int choiceId = Integer.parseInt(choiceParts[1]);
                String field = parts[1];

                // find or create choice
                Choice choice = choiceList.stream()
                        .filter(c -> c.getId() == choiceId)
                        .findFirst()
                        .orElseGet(() -> {
                            Choice newChoice = new Choice();
                            newChoice.setId(choiceId);
                            newChoice.setQuestionId(questionId);
                            choiceList.add(newChoice);
                            return newChoice;
                        });
                // set values
                if (field.equals("text")) {
                    choice.setText(entry.getValue());
                } else if (field.equals("isAnswer")) {
                    choice.setAnswer(true);
                }
            }
        }

        // update question text
        questionDAO.updateQuestion(questionId, text);

        // update choices
        for (Choice choice : choiceList) {
            choiceDAO.updateChoice(choice);
        }

        return "redirect:/questionManagement";
    }

    @PostMapping("/saveNewQuestion")
    public String saveNewQuestion(@RequestParam("categoryId") int categoryId,
                                  @RequestParam("text") String text,
                                  @RequestParam Map<String, String> choices) {
        Question question = new Question();
        question.setCategoryId(categoryId);
        question.setText(text);

        // do choices
        List<Choice> choiceList = new ArrayList<>();
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            if (entry.getKey().startsWith("choices")) {
                String[] parts = entry.getKey().split("\\.");
                String choiceIdString = parts[0];
                String[] choiceParts = choiceIdString.split("[\\[\\]]");
                int index = Integer.parseInt(choiceParts[1]);
                String field = parts[1];

                Choice choice = choiceList.stream()
                        .filter(c -> c.getId() == index)
                        .findFirst()
                        .orElseGet(() -> {
                            Choice newChoice = new Choice();
                            newChoice.setId(index);
                            choiceList.add(newChoice);
                            return newChoice;
                        });
                if (field.equals("text")) {
                    choice.setText(entry.getValue());
                } else if (field.equals("isAnswer")) {
                    choice.setAnswer(true);
                }
            }
        }


        // set question
        int questionId = questionDAO.addQuestion(question);
        for (Choice choice : choiceList) {
            choice.setQuestionId(questionId);
            choiceDAO.createChoice(choice);
        }

        return "redirect:/questionManagement";
    }
}
