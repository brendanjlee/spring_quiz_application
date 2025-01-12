package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.DTO.CategoryDTO;
import org.example.spring_quiz_application.DTO.QuestionDTO;
import org.example.spring_quiz_application.DTO.QuizResultDTO;
import org.example.spring_quiz_application.DTO.UserDTO;
import org.example.spring_quiz_application.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/admin/userManagement";
    }

    @GetMapping("quizResultManagement")
    public String getQuizResultManagement(@RequestParam(value = "category", required = false) Integer categoryId,
                                          @RequestParam(value = "user", required = false) Integer userId,
                                          HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (!validUser(session)) {
            return "redirect:/";
        }

        try {
            // 1. quiz results
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<List<QuizResultDTO>> quizResultResponse = restTemplate.exchange(
                    baseUrl + "/api/quiz/results",
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<QuizResultDTO>>() {
                    }
            );
            List<QuizResultDTO> results = quizResultResponse.getBody();

            // 2. users
            ResponseEntity<List<UserDTO>> usersResponse = restTemplate.exchange(
                    baseUrl + "api/users/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDTO>>() {
                    }
            );
            Map<Integer, String> userNameMap = new HashMap<>();
            for (UserDTO user : Objects.requireNonNull(usersResponse.getBody())) {
                userNameMap.put(user.getId(), user.getFirstName() + " " + user.getLastName());
            }

            // 3. categories
            ResponseEntity<List<CategoryDTO>> categoryResponse = restTemplate.exchange(
                    baseUrl + "api/quiz/categories",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CategoryDTO>>() {
                    }
            );
            Map<Integer, String> categoryNameMap = new HashMap<>();
            for (CategoryDTO category : Objects.requireNonNull(categoryResponse.getBody())) {
                categoryNameMap.put(category.getId(), category.getName());
            }

            // 4. update some form specific fields
            for (QuizResultDTO quizResultDTO : Objects.requireNonNull(results)) {
                // update fullname
                UserDTO user = Objects.requireNonNull(usersResponse.getBody())
                        .stream()
                        .filter(u -> u.getId() == quizResultDTO.getUserId())
                        .findFirst()
                        .orElse(null);
                quizResultDTO.setUserFullName(user != null ? user.getFirstName()
                        + " " + user.getLastName() : "Unknown");

                // update num questions
                quizResultDTO.setNumQuestions(quizResultDTO.getQuestions().size());
            }

            // 5. filter based on needs
            if (categoryId != null || userId != null) {
                results = results.stream()
                        .filter(result -> {
                            boolean matchesCat =
                                    categoryId == null || result.getCategoryName().equals(categoryNameMap.get(categoryId));
                            boolean matchesUser =
                                    userId == null || result.getUserFullName().equals(userNameMap.get(userId));
                            return matchesCat && matchesUser;
                        })
                        .collect(Collectors.toList());
            }

            model.addAttribute("users", usersResponse.getBody());
            model.addAttribute("categories", categoryResponse.getBody());
            model.addAttribute("quizResults", results);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "adminQuizResult";
    }

    @GetMapping("contactUsManagement")
    public String getContactUsManagement() {
        return "contactUsManagement";
    }
}
