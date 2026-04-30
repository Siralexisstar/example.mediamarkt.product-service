package com.example.mediamarkt.product.infraestructure.persistence;

import com.example.mediamarkt.product.application.port.CategoryRepositoryPort;
import com.example.mediamarkt.product.domain.model.Category;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.CategoryDocument;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.ReactiveCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

  private final ReactiveCategoryRepository categoryRepo;

  @Override
  public Mono<Category> save(Category category) {
    return categoryRepo.save(CategoryDocument.fromDomain(category)).map(CategoryDocument::toDomain);
  }

  @Override
  public Mono<Category> findById(String id) {
    return categoryRepo.findById(id).map(CategoryDocument::toDomain);
  }

  @Override
  public Flux<Category> findAll() {
    return categoryRepo.findAll().map(CategoryDocument::toDomain);
  }

  @Override
  public Mono<Void> delete(String id) {
    return categoryRepo.deleteById(id);
  }

  @Override
  public Mono<Void> deleteAll() {
    return categoryRepo.deleteAll();
  }
}
