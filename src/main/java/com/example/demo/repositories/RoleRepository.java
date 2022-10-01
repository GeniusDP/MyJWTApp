package com.example.demo.repositories;

import com.example.demo.models.entities.Role;
import com.example.demo.models.entities.RoleValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleValue name);
}
