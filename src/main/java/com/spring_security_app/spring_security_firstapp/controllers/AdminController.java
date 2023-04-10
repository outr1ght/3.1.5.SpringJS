package com.spring_security_app.spring_security_firstapp.controllers;

import com.spring_security_app.spring_security_firstapp.entities.Role;
import com.spring_security_app.spring_security_firstapp.entities.User;
import com.spring_security_app.spring_security_firstapp.repositories.RoleRepository;
import com.spring_security_app.spring_security_firstapp.service.RegistrationServiceImpl;
import com.spring_security_app.spring_security_firstapp.service.UserService;
import com.spring_security_app.spring_security_firstapp.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationServiceImpl registrationServiceImpl;
    private final UserValidator userValidator;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RegistrationServiceImpl registrationServiceImpl, UserValidator userValidator, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.registrationServiceImpl = registrationServiceImpl;
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/main-page")
    public String getAllUsers(Model model, Principal principal, @ModelAttribute("user") User user) {

        User admin = userService.getUserByUsername(principal.getName());
        model.addAttribute("admin", admin);
        model.addAttribute("userRole", admin.getRoles());
        model.addAttribute("users", userService.getAllUsers());

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "/admin/main-page";
    }


    @GetMapping("/create-user")
    public String creationPage(@ModelAttribute("user") User user) {
        return "/admin/create-user";
    }

    @PostMapping("/create-user")
    public String newUser(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/admin/create-user";
        }

        registrationServiceImpl.register(user);

        return "redirect:/admin/main-page";
    }


    @GetMapping("/{id}/show-info")
    public String showInfo(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRoles());
        return "/admin/show-info";
    }


    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "/admin/edit-user";
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") @Valid User user, @RequestParam("id") Long id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/edit-user";
        }
        userService.updateUser(id, user);
        return "redirect:/admin/main-page";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin/main-page";
    }


}
