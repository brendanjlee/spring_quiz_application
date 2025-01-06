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

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final UserDAO userDAO;
    private final ContactDAO contactDAO;
    private final QuestionDAO questionDAO;

    public AdminController(UserDAO userDAO, ContactDAO contactDAO,
                           QuestionDAO questionDAO) {
        this.userDAO = userDAO;
        this.contactDAO = contactDAO;
        this.questionDAO = questionDAO;
    }

    @PostMapping("toggleActiveStatus")
    public String toggleActiveStatus(@RequestParam("userId") int userId,
                                     HttpServletRequest request) {
        System.out.println("toggleActiveStatus");
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
}
