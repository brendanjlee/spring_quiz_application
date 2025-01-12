package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminUiController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/";

    // admin homepage
    @GetMapping("")
    public String getAdminHome(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("session or user is null");
            return "redirect:/login";
        }

        // check admin
        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            return "redirect:/";
        }

        return "adminHome";
    }

    @GetMapping("userManagement")
    public String getUserManagement() {
        return "userManagement";
    }

    @GetMapping("quizResultManagement")
    public String getQuizResultManagement() {
        return "quizResultManagement";
    }

    @GetMapping("contactUsManagement")
    public String getContactUsManagement() {
        return "contactUsManagement";
    }
}
