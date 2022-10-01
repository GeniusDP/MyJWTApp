package com.example.demo.controllers;

import com.example.demo.models.entities.Role;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RolesController {

    @Autowired
    private RoleRepository repository;

    @GetMapping("/roles")
    public List<Role> getAllRolesInTheSystem(){
        return repository.findAll();
    }
}
