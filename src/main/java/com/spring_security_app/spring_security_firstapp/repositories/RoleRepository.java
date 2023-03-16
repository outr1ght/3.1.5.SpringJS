package com.spring_security_app.spring_security_firstapp.repositories;

import com.spring_security_app.spring_security_firstapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
