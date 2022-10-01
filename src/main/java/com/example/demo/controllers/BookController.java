package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @GetMapping("/books")
    public List<String> getAllBooks(){
        return List.of("Война и мир", "Harry Potter", "Ведьмак");
    }
}
