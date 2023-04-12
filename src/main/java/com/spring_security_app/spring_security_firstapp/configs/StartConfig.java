package com.spring_security_app.spring_security_firstapp.configs;

import com.spring_security_app.spring_security_firstapp.entities.Role;
import com.spring_security_app.spring_security_firstapp.entities.User;
import com.spring_security_app.spring_security_firstapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class StartConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public StartConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        Collection<Role> userRoles = List.of(userRole);
        Collection<Role> adminRoles = List.of(adminRole);


        if (userRepository.findByUsername("user").isEmpty()) {

            User user = new User();
            user.setUsername("user");
            user.setAge(22);
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(userRoles);

            userRepository.save(user);
        }
        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setAge(32);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(adminRoles);

            userRepository.save(admin);
        }
    }
}
