package com.example.mediamarkt.product.interfaces.controllers;

import com.example.mediamarkt.product.interfaces.controllers.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

  @GetMapping("hellow/category")
  @Operation(summary = "Say hellow to category")
  public Mono<CategoryDto> hellowProducts(CategoryDto categoryDto) {
    return Mono.just(categoryDto);
  }

  // CRUD CONTROLLERS
}
