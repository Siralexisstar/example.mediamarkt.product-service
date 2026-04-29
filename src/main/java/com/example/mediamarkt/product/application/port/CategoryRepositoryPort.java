package com.example.mediamarkt.product.application.port;

import com.example.mediamarkt.product.domain.model.Category;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepositoryPort {

    Mono<Category> save(Category category);

    Mono<Category> findById(String id);

    Flux<Category> findAll();

    Mono<Void> delete(String id);

    Mono<Void> deleteAll();

}
