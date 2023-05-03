package com.spring_security_app.spring_security_firstapp.controllers;

import com.spring_security_app.spring_security_firstapp.entities.User;
import com.spring_security_app.spring_security_firstapp.service.UserService;
import com.spring_security_app.spring_security_firstapp.util.UserNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RESTController {


    private final UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsersList() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user) {

        userService.add(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new UserNotCreatedException(errorMessage.toString());
        }

        userService.updateUser(user.getId(), user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
