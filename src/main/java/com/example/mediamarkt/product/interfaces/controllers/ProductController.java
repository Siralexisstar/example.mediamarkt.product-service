package com.example.mediamarkt.product.interfaces.controllers;

import com.example.mediamarkt.product.interfaces.controllers.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

  @GetMapping("hellow/products")
  @Operation(summary = "Say hellow to product")
  public Mono<ProductDto> hellowProducts(ProductDto productDto) {
    return Mono.just(productDto);
  }
}
