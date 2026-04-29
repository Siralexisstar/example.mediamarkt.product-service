package com.example.mediamarkt.product.application.port;

import com.example.mediamarkt.product.domain.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {

    Mono<Product> save(Product product);

    Mono<Product> findById(Product product);

    Flux<Product> findAll();

    Mono<Product> updateProduct(Product product);

    Mono<Void> delete(Product product);

}
