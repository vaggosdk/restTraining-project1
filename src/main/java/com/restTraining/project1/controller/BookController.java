package com.restTraining.project1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/api-endpoint")
    public String firstApi() {
        return "Hello, this is a response from the API endpoint!";
    }
}
