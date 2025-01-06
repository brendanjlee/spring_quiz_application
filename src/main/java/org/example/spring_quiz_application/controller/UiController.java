package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.*;
import org.example.spring_quiz_application.service.AdminService;
import org.example.spring_quiz_application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

@Controller
public class UiController {
    private final QuizService quizService;
    private final AdminService adminService;

    @Autowired
    public UiController(QuizService quizService, AdminService adminService) {
        this.quizService = quizService;
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        // redirect to log in if user is not in session
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }

        // get all quiz categories
        List<Category> categories = quizService.getAllCategories();
        model.addAttribute("categories", categories);

        // get recent quizzes for logged in user
        User user = (User) session.getAttribute("user");
        List<QuizResult> quizResults =
                quizService.getAllQuizResultsByUserId(user.getId());

        model.addAttribute("quizResults", quizResults);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/contact")
    public String contactUs() {
        return "contactForm";
    }

    @GetMapping("/quiz")
    public String quiz(Model model) {
        return "quiz";
    }

    @GetMapping("quiz/result/{quizResultId}")
    public String getQuizResult(@PathVariable int quizResultId, Model model) {
        // get quiz result
        QuizResult quizResult = quizService.getQuizResultById(quizResultId);
        quizResult.setCategoryName(quizService.getCategoryNameById(quizResult.getCategoryId()));

        // get questions for the category
        List<Question> questions =
                quizService.getAllQuestionsByCategoryId(quizResult.getCategoryId());
        quizService.mapChoicesToQuestions(questions);

        // map answers & calculate results
        int result = 0;
        questions.forEach(question -> {
            question.getChoices().forEach(choice -> {
                quizService.mapQuizQuestionsToChoices(choice, quizResultId);
                System.out.println(choice);
            });
        });

        for (Question question : questions) {
            for (Choice choice : question.getChoices()) {
                if (choice.isAnswer() && choice.isUserAnswer()) result++;
            }
        }

        model.addAttribute("quizResult", quizResult);
        model.addAttribute("questions", questions);
        model.addAttribute("result", result);
        return "quizResult";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "adminHome";
    }

    @GetMapping("/userManagement")
    public String userManagement(Model model) {
        // get all users
        List<User> users = adminService.getAllUsers();
        System.out.println(users);
        model.addAttribute("users", users);

        return "adminUserManagement";
    }
}
