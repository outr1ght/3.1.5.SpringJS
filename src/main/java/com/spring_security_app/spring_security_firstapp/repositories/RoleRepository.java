package com.spring_security_app.spring_security_firstapp.repositories;

import com.spring_security_app.spring_security_firstapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

}
