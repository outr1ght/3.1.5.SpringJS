package com.spring_security_app.spring_security_firstapp.service;


import com.spring_security_app.spring_security_firstapp.entities.User;
import com.spring_security_app.spring_security_firstapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    @Lazy
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    @Transactional
    public void updateUser(long id, User updateUser) {
        User userToUpdate = userRepository.findById(id).get();
        if (userToUpdate.getPassword().equals(updateUser.getPassword())) {
            userRepository.save(updateUser);
        } else {
            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            userRepository.save(updateUser);
        }


    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

}
