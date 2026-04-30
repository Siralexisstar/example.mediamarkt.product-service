package com.example.mediamarkt.product.interfaces.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

  @GetMapping("/hello")
  @Operation(summary = "Returns a greeting message")
  public String hello() {
    return "Hello, World!";
  }
}
