package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.Category;
import org.example.spring_quiz_application.domain.QuizResult;
import org.example.spring_quiz_application.domain.User;
import org.example.spring_quiz_application.service.CategoryService;
import org.example.spring_quiz_application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UiController {
    private final QuizService quizService;

    @Autowired
    public UiController(QuizService quizService) {
        this.quizService = quizService;
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
        System.out.println(quizResults);

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
}
