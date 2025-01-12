package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DTO.UserDTO;
import org.example.spring_quiz_application.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUiController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/";

    private boolean validUser(HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        return user.isAdmin();
    }

    // admin homepage
    @GetMapping("")
    public String getAdminHome(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (!validUser(session)) {
            return "redirect:/";
        }

        return "adminHome";
    }

    @GetMapping("userManagement")
    public String getUserManagement(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (!validUser(session)) {
            return "redirect:/";
        }

        try {
            ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                    baseUrl + "api/users/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDTO>>() {
                    }
            );

            model.addAttribute("users", response.getBody());

            return "adminUserManagement";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "redirect:/";
        }
    }

    // toggle endpoint. call backend, then reload the page
    @PostMapping("toggleActive/{userId}")
    public String toggleActive(@PathVariable("userId") int userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (!validUser(session)) {
            return "redirect:/";
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create an empty HttpEntity since nobody is needed for this request
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // Make the PATCH request
            restTemplate.exchange(
                    baseUrl + "/api/admin/users/" + userId + "/toggleActive",
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
            );

            return "redirect:/admin/userManagement";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("toggleAdmin/{userId}")
    public String toggleAdmin(@PathVariable("userId") int userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (!validUser(session)) {
            return "redirect:/";
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            restTemplate.exchange(
                    baseUrl + "/api/admin/users/" + userId + "/toggleAdmin",
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
            );
            return "redirect:/admin/userManagement";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/";
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
