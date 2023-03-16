package com.spring_security_app.spring_security_firstapp.service;



import com.spring_security_app.spring_security_firstapp.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void add(User user);

    User getUserById(long id);

    User getUserByUsername(String username);

    void updateUser(long id, User updateUser);

    void deleteUser(long id);


}
