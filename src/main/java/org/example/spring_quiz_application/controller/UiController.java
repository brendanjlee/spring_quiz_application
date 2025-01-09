package org.example.spring_quiz_application.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UiController {
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        return "redirect:/login";
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
