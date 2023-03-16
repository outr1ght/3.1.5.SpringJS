package com.spring_security_app.spring_security_firstapp.configs;

import com.spring_security_app.spring_security_firstapp.entities.Role;
import com.spring_security_app.spring_security_firstapp.entities.User;
import com.spring_security_app.spring_security_firstapp.repositories.RoleRepository;
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

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public StartConfig(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Collection<Role> userRoles;
        Collection<Role> adminRoles;

        if (userRepository.findByUsername("user").isEmpty()) {

            User user = new User();
            user.setUsername("user");
            user.setAge(22);
            user.setPassword(passwordEncoder.encode("user"));

            if (roleRepository.findByRole("ROLE_USER").isEmpty()){
                Role userRole = new Role();
                userRole.setRole("ROLE_USER");
                userRoles = List.of(userRole);
                user.setRoles(userRoles);
            }

            userRepository.save(user);
        }
        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setAge(32);
            admin.setPassword(passwordEncoder.encode("admin"));
            if (roleRepository.findByRole("ROLE_ADMIN").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setRole("ROLE_ADMIN");

                adminRoles = List.of(adminRole);
                admin.setRoles(adminRoles);
            }

            userRepository.save(admin);
        }
    }
}
