package com.example.mediamarkt.product.application.port;

import com.example.mediamarkt.product.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {

  Mono<Product> save(Product product);

  Mono<Product> findById(String id);

  Flux<Product> findAll();

  Mono<Void> delete(Product product);
}
