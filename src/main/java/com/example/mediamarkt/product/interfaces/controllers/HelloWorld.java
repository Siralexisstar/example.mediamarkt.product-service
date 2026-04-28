package com.example.mediamarkt.product.interfaces.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class HelloWorld {
    
    @GetMapping("/hello")
    @Operation(summary = "Returns a greeting message")
    public String hello() {
        return "Hello, World!";
    }
    
}
