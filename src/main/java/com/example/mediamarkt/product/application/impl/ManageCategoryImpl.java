package com.example.mediamarkt.product.application.impl;

import com.example.mediamarkt.product.application.port.CategoryRepositoryPort;
import com.example.mediamarkt.product.domain.exception.ResourceNotFoundException;
import com.example.mediamarkt.product.domain.model.Category;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManageCategoryImpl {

  private final CategoryRepositoryPort categoryRepositoryPort;

  public Mono<Category> createCategory(Category category) {
    return categoryRepositoryPort.save(category);
  }

  public Mono<Category> getCategory(String id) {
    return categoryRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Category not found")));
  }

  public Flux<Category> getAllCategories() {
    return categoryRepositoryPort.findAll();
  }

  public Mono<Category> updateCategory(String id, Category category) {
    return categoryRepositoryPort
        .findById(id)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Category not found")))
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

  public Mono<List<Category>> getCategoryPath(String categoryId) {
    return getCategory(categoryId)
        .expandDeep(
            category -> {
              if (category.getParentId() != null && !category.getParentId().isEmpty()) {
                log.debug("Searching the parentId, {}", category.getParentId());
                log.debug("Current Category name: {}", category.getName());
                return categoryRepositoryPort.findById(category.getParentId());
              }
              return Mono.empty();
            })
        .take(30) // Add a safety measure to prevent infinite loops, aproximated to the depth of
        // the tree.
        .collectList()
        .map(
            list -> {
              List<Category> entirePath = new ArrayList<>(list);
              Collections.reverse(entirePath);
              return entirePath;
            })
        .doOnError(v -> Mono.error(new ResourceNotFoundException("Category not found")));
  }
}
