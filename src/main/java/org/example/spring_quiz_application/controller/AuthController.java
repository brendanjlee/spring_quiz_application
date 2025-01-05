package org.example.spring_quiz_application.controller;

//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.example.spring_quiz_application.domain.User;
import org.example.spring_quiz_application.service.AuthService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // todo redirect to quiz if logged in already
//    @GetMapping("/login")
//    public String getLogin(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//
//    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("userEmail") String email,
                            @RequestParam("password") String password,
                            HttpServletRequest request
    ) {
        System.out.println("Login Requested");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        Optional<User> possibleUser = authService.validateLogin(email,
                password);

        if (possibleUser.isPresent()) {
            HttpSession oldSession = request.getSession(false);

            // invalidate old session
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // generate new session
            HttpSession newSession = request.getSession(true);

            // store user details
            newSession.setAttribute("user", possibleUser.get());

            System.out.println("New Session: " + newSession.getId());
            System.out.println("User: " + newSession.getAttribute("user"));

            return "redirect:/";
        }

        System.out.println("Wrong password");
        return "login";
    }

    @PostMapping("/register")
    public boolean postRegister(@RequestBody User user) {
        System.out.println("register");
        return true;
    }

    @PostMapping("/logout")
    public String postLogout(HttpServletRequest request, Model model) {
        System.out.println("logout requested");
        HttpSession oldSession = request.getSession(false);
        // invalidate old session
        if (oldSession != null) {
            oldSession.invalidate();
        }
        return "login";
    }
}
