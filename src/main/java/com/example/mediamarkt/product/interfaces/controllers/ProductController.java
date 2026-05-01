package com.example.mediamarkt.product.interfaces.controllers;

import com.example.mediamarkt.product.application.impl.ManageProductsImpl;
import com.example.mediamarkt.product.interfaces.controllers.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ManageProductsImpl productsImpl;

  @GetMapping("hellow/products")
  @Operation(summary = "Say hellow to product")
  public Mono<ProductDto> hellowProducts(ProductDto productDto) {
    return Mono.just(productDto);
  }

  // CRUD CONTROLLERS

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create new product")
  public Mono<ProductDto> create(@RequestBody Mono<ProductDto> productDto) {
    return productDto
        .map(ProductDto::toDomain)
        .flatMap(productsImpl::createProduct)
        .map(ProductDto::fromDomain);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find a product by ID")
  public Mono<ProductDto> getById(@PathVariable String id) {

    return productsImpl.getProduct(id).map(ProductDto::fromDomain);
  }

  @GetMapping("/all")
  @Operation(summary = "Get all products")
  public Flux<ProductDto> getAllProducts() {
    return productsImpl.getAllProducts().map(ProductDto::fromDomain);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update product by Id")
  public Mono<ProductDto> update(
      @PathVariable String id, @RequestBody Mono<ProductDto> productDto) {

    return productDto
        .map(ProductDto::toDomain)
        .flatMap(domain -> productsImpl.updateProduct(id, domain))
        .map(ProductDto::fromDomain);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete a product")
  public Mono<Void> delete(@PathVariable String id) {
    return productsImpl.deleteProduct(id);
  }
}
