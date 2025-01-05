package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.Choice;
import org.example.spring_quiz_application.domain.Question;
import org.example.spring_quiz_application.domain.User;
import org.example.spring_quiz_application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public String getQuiz(Model model, HttpServletRequest request) {

        // redirect to login if no user
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "quiz";
    }

    @PostMapping
    public String postStartQuiz(@RequestParam("category") int categoryId,
                                Model model, HttpServletRequest request) {
        // attach startTime to session
        LocalDateTime startTime = LocalDateTime.now();
        request.getSession().setAttribute("startTime", startTime);
        request.getSession().setAttribute("categoryId", categoryId);

        // use service to give it category id and spit out questions
        String categoryName = quizService.getCategoryNameById(categoryId);
        // use service to get question id and get choices
        List<Question> questions =
                quizService.getAllQuestionsByCategoryId(categoryId);

        for (Question question : questions) {
            List<Choice> choices =
                    quizService.getAllChoicesByQuestionId(question.getId());
            question.setChoices(choices);
        }

        System.out.println("Start Quiz");
        System.out.println("categoryId: " + categoryId);
        System.out.println("questions: " + questions);

        // attach to model
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(HttpServletRequest request, Model model) {
        // get {question:choice} pairing
        // should also take in time start and user id
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<Integer, Integer> quizAnswers = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.startsWith("question_")) {
                int questionId = Integer.parseInt(parameterName.substring(9));
                int choiceId =
                        Integer.parseInt(request.getParameter(parameterName));
                quizAnswers.put(questionId, choiceId);
            }
        }

        // get session information
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int categoryId = (Integer) session.getAttribute("categoryId");
        LocalDateTime startTime = (LocalDateTime) session.getAttribute(
                "startTime");
        LocalDateTime endTime = LocalDateTime.now();

        System.out.println("====Submit====\n" + quizAnswers.toString());
        System.out.println("User ID: " + user.getId());
        System.out.println("Category ID: " + categoryId);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);

        // process quiz and redirect to results
        quizService.submitQuiz(user.getId(), categoryId, startTime, endTime,
                quizAnswers);

        return "wopw";
    }
}
