package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.*;
import org.example.spring_quiz_application.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final UserDAO userDAO;

    public AdminController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("toggleActiveStatus")
    public String toggleActiveStatus(@RequestParam("userId") int userId,
                                     HttpServletRequest request) {
        System.out.println("toggleActiveStatus");
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") == null) {
            System.out.println("No active user in session");
            return "redirect:/";
        }
        User currrentUser = (User) session.getAttribute("user");
        if (!currrentUser.isAdmin()) {
            System.out.println("Current user is not admin");
            return "redirect:/";
        }

        // get the request user and update
        User user = userDAO.getUserById(userId);
        if (user.isActive()) {
            userDAO.deactivateUser(userId);
        } else {
            userDAO.activateUser(userId);
        }

        return "redirect:/userManagement";
    }
}
