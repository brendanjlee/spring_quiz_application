package org.example.spring_quiz_application.controller;


import org.example.spring_quiz_application.DTO.QuizResultDTO;
import org.example.spring_quiz_application.model.Category;
import org.example.spring_quiz_application.model.QuizResult;
import org.example.spring_quiz_application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class UiController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/";

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // redirect if not logged in
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("session or user is null");
            return "redirect:/login";
        }

        try {
            // 1. fetch category names
            ResponseEntity<List<Category>> categoryResponse =
                    restTemplate.exchange(
                            baseUrl + "api/quiz/categories",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Category>>() {
                            }
                    );

            System.out.println(categoryResponse.getBody());
            model.addAttribute("categories", categoryResponse.getBody());

            // 2. fetch quiz results
            User user = (User) session.getAttribute("user");
            if (!user.isActive()) return "redirect:/login";

            // get quiz results by user id
            ResponseEntity<List<QuizResultDTO>> quizResultResponse =
                    restTemplate.exchange(
                            baseUrl + "api/quiz/results/user/" + user.getId(),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<QuizResultDTO>>() {
                            }
                    );
            model.addAttribute("quizResults", quizResultResponse.getBody());

            return "index"; // todo change to index
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String email,
                            @RequestParam String password, Model model,
                            HttpServletRequest request) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);

            // make post request
            ResponseEntity<User> response = restTemplate.postForEntity(
                    baseUrl + "api/auth/login", requestBody, User.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // attach to session
                User user = response.getBody();
                HttpSession oldSession = request.getSession(false);

                // invalidate old session
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                // create new session
                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("user", user);
                System.out.println("New Session: " + newSession.getId());
                System.out.println("User: " + newSession.getAttribute("user"));
                System.out.println("successful login: " + response.getBody());
                return "redirect:/";
            } else {
                System.out.println("unsuccessful login");
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.out.println("unsuccessful login");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("password") String password) {
        try {
            // build request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("firstName", firstName);
            requestBody.put("lastName", lastName);
            requestBody.put("password", password);

            // make post request to API
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl + "api/auth/register", requestBody, String.class);

            // check response and redirect
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Registered Successfully");
                return "redirect:/login";
            } else {
                return "redirect:/register";
            }

        } catch (Exception e) {
            // do something about error
            return "redirect:/register";
        }
    }

    @PostMapping("/logout")
    public String postLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        System.out.println("logout");
        return "redirect:/login";
    }

    @GetMapping("/contact")
    public String getContact() {
        return "/contactForm";
    }

    @PostMapping("/contact")
    public String postContact(@RequestParam("email") String email,
                              @RequestParam("subject") String subject,
                              @RequestParam("message") String message) {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("subject", subject);
            requestBody.put("message", message);

            // create response entity
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl + "api/admin/contacts", requestBody, String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Contact Message Created Successfully");
            }
            return "/contactForm";
        } catch (Exception e) {
            return "/contactForm";
        }
    }

    @GetMapping("/quiz/result/{quizResultId}")
    public String getQuizResult(@PathVariable("quizResultId") String quizResultId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // redirect if not logged in
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("session or user is null");
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        // fetch from backend /api/quiz/results/{quizResultId}
        // attach to model
        // [] quizResult -> categoryName
        // [] questions & questions.choices
        try {
            // session username
            ResponseEntity<QuizResultDTO> response = restTemplate.getForEntity(
                    baseUrl + "api/quiz/results/" + user.getId() + "/" + quizResultId, QuizResultDTO.class
            );

            QuizResultDTO quizResultDTO = response.getBody();
            model.addAttribute("quizResult", quizResultDTO);

            return "redirect:/quizResult";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/placeHolder")
    public String placeHolder() {
        return "placeHolder";
    }
}
