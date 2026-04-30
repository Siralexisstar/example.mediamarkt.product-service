package com.example.mediamarkt.product.application.impl;

import com.example.mediamarkt.product.application.port.CategoryRepositoryPort;
import com.example.mediamarkt.product.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ManageCategoryImpl {

  private final CategoryRepositoryPort categoryRepositoryPort;

  public Mono<Category> createCategoty(Category category) {

    return categoryRepositoryPort.save(category);
  }

  public Mono<Category> getCategory(String id) {

    return categoryRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new Exception("Product not found")));
  }

  public Flux<Category> getAllCategories() {
    return categoryRepositoryPort.findAll();
  }

  public Mono<Category> updateCategory(String id, Category category) {
    return categoryRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new Exception("Product not found")))
        .flatMap(
            c -> {
              category.setId(id);
              return categoryRepositoryPort.save(category);
            });
  }

  public Mono<Void> deleteCategoryById(String id) {
    return categoryRepositoryPort.delete(id);
  }

  public Mono<Void> deleteCategories() {
    return categoryRepositoryPort.deleteAll();
  }
}
