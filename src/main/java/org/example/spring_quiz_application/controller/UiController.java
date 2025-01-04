package org.example.spring_quiz_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UiController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

}
