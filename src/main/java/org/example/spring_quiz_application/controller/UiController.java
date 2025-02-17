package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DTO.QuestionDTO;
import org.example.spring_quiz_application.DTO.QuizResultPageDTO;
import org.example.spring_quiz_application.dao.ChoiceDAO;
import org.example.spring_quiz_application.dao.QuestionDAO;
import org.example.spring_quiz_application.domain.*;
import org.example.spring_quiz_application.service.AdminService;
import org.example.spring_quiz_application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

@Controller
public class UiController {
    private final QuizService quizService;
    private final AdminService adminService;
    private final QuestionDAO questionDAO;
    private final ChoiceDAO choiceDAO;

    @Autowired
    public UiController(QuizService quizService, AdminService adminService,
                        QuestionDAO questionDAO, ChoiceDAO choiceDAO) {
        this.quizService = quizService;
        this.adminService = adminService;
        this.questionDAO = questionDAO;
        this.choiceDAO = choiceDAO;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        // redirect to log in if user is not in session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login";
        }


        // get all quiz categories
        List<Category> categories = quizService.getAllCategories();
        model.addAttribute("categories", categories);

        // get recent quizzes for logged in user
        User user = (User) session.getAttribute("user");
        // suspended
        if (!user.isActive()) return "redirect:/";

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
    public String admin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/";
        }

        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) return "redirect:/";
        return "adminHome";
    }

    @GetMapping("/userManagement")
    public String userManagement(HttpServletRequest request, Model model) {
        if (!adminService.isAdmin(request)) return "redirect:/";
        // get all users
        List<User> users = adminService.getAllUsers();
        System.out.println(users);
        model.addAttribute("users", users);

        return "adminUserManagement";
    }

    @GetMapping("/quizResultManagement")
    public String quizResultManagement(@RequestParam(value = "category",
                                               required = false) Integer categoryId,
                                       @RequestParam(value = "user",
                                               required = false) Integer userId,
                                       Model model,
                                       HttpServletRequest request) {
        if (!adminService.isAdmin(request)) return "redirect:/";
        List<QuizResultPageDTO> results = adminService.getAllQuizResults();
        List<QuizResultPageDTO> filteredResults =
                adminService.getFilteredQuizResults(results, categoryId,
                        userId);
        List<Category> categories = quizService.getAllCategories();
        List<User> users = adminService.getAllUsers();

        model.addAttribute("categories", categories);
        model.addAttribute("results", filteredResults);
        model.addAttribute("users", users);

        return "adminQuizResult";
    }

    @GetMapping("/contactUsManagement")
    public String contactUsManagement(Model model, HttpServletRequest request) {
        if (!adminService.isAdmin(request)) return "redirect:/";
        List<Contact> contacts = adminService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "adminContact";
    }

    @GetMapping("/questionManagement")
    public String questionManagement(Model model, HttpServletRequest request) {
        if (!adminService.isAdmin(request)) return "redirect:/";
        List<QuestionDTO> questions = adminService.getAllQuestionsDTO();
        model.addAttribute("questions", questions);
        return "adminQuestionManagement";
    }

    @GetMapping("/admin/editQuestion")
    public String editQuestion(@RequestParam("questionId") int questionId,
                               Model model, HttpServletRequest request) {
        if (!adminService.isAdmin(request)) return "redirect:/";
        Question question = questionDAO.getQuestion(questionId);
        List<Choice> choices = choiceDAO.getChoicesByQuestionId(questionId);
        model.addAttribute("choices", choices);
        model.addAttribute("question", question);

        return "editQuestion";
    }

    @GetMapping("/admin/addQuestion")
    public String addQuestion(Model model, HttpServletRequest request) {
        if (!adminService.isAdmin(request)) return "redirect:/";
        List<Category> categories = quizService.getAllCategories();
        model.addAttribute("categories", categories);
        return "newQuestion";
    }
}
