package com.spring_security_app.spring_security_firstapp.controllers;


import com.spring_security_app.spring_security_firstapp.entities.User;
import com.spring_security_app.spring_security_firstapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showInfoAboutUser(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user";
    }
}
