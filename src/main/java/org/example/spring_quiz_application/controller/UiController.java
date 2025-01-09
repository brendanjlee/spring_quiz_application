package org.example.spring_quiz_application.controller;


import org.example.spring_quiz_application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/")
public class UiController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/";

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("session or user is null");
            return "redirect:/login";
        }

        // fetch other category stuff

        return "redirect:/placeHolder";
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

    @GetMapping("/placeHolder")
    public String placeHolder() {
        return "placeHolder";
    }
}
