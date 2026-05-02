package com.example.mediamarkt.product.application.impl;

import com.example.mediamarkt.product.application.port.ProductRepositoryPort;
import com.example.mediamarkt.product.domain.exception.ResourceNotFoundException;
import com.example.mediamarkt.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ManageProductsImpl {

  private final ProductRepositoryPort productRepositoryPort;

  public Mono<Product> createProduct(Product product) {
    return productRepositoryPort.save(product);
  }

  public Mono<Product> getProduct(String id) {
    return productRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found")));
  }

  public Flux<Product> getAllProducts() {
    return productRepositoryPort.findAll();
  }

  public Mono<Product> updateProduct(String id, Product product) {
    return productRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found")))
        .flatMap(
            p -> {
              product.setId(id);
              return productRepositoryPort.save(product);
            });
  }

  public Mono<Void> deleteProduct(String id) {
    return productRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product not found")))
        .flatMap(
            p -> {
              return productRepositoryPort.delete(p);
            });
  }
}
