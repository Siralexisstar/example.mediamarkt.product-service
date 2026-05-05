package com.example.mediamarkt.product.unit.infraestructure.persistence;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.domain.model.Category;
import com.example.mediamarkt.product.infraestructure.persistence.CategoryRepositoryAdapter;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.CategoryDocument;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.ReactiveCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryAdapterTest {

  @Mock private ReactiveCategoryRepository categoryRepo;

  @InjectMocks private CategoryRepositoryAdapter adapter;

  private final String CATEGORY_ID = "1";
  private final String CATEGORY_NAME = "Electronics";

  @Test
  @DisplayName("Should save category and map back to domain")
  void should_saveCategory_when_save() {
    // Arrange
    Category domainCategory = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
    CategoryDocument savedDocument =
        CategoryDocument.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();

    when(categoryRepo.save(any(CategoryDocument.class))).thenReturn(Mono.just(savedDocument));

    // Act
    Mono<Category> result = adapter.save(domainCategory);

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(c -> c.getId().equals(CATEGORY_ID) && c.getName().equals(CATEGORY_NAME))
        .verifyComplete();

    verify(categoryRepo).save(any(CategoryDocument.class));
    verifyNoMoreInteractions(categoryRepo);
  }

  @Test
  @DisplayName("Should return category when found by id")
  void should_returnCategory_when_findById() {
    // Arrange
    CategoryDocument document =
        CategoryDocument.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();

    when(categoryRepo.findById(CATEGORY_ID)).thenReturn(Mono.just(document));

    // Act
    Mono<Category> result = adapter.findById(CATEGORY_ID);

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(c -> c.getId().equals(CATEGORY_ID) && c.getName().equals(CATEGORY_NAME))
        .verifyComplete();

    verify(categoryRepo).findById(CATEGORY_ID);
    verifyNoMoreInteractions(categoryRepo);
  }

  @Test
  @DisplayName("Should return all categories")
  void should_returnAllCategories_when_findAll() {
    // Arrange
    CategoryDocument doc1 = CategoryDocument.builder().id("1").name("Cat 1").build();
    CategoryDocument doc2 = CategoryDocument.builder().id("2").name("Cat 2").build();

    when(categoryRepo.findAll()).thenReturn(Flux.just(doc1, doc2));

    // Act
    Flux<Category> result = adapter.findAll();

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(c -> c.getId().equals("1") && c.getName().equals("Cat 1"))
        .expectNextMatches(c -> c.getId().equals("2") && c.getName().equals("Cat 2"))
        .verifyComplete();

    verify(categoryRepo).findAll();
    verifyNoMoreInteractions(categoryRepo);
  }

  @Test
  @DisplayName("Should complete when delete by id is successful")
  void should_complete_when_delete() {
    // Arrange
    when(categoryRepo.deleteById(CATEGORY_ID)).thenReturn(Mono.empty());

    // Act
    Mono<Void> result = adapter.delete(CATEGORY_ID);

    // Assert
    StepVerifier.create(result).verifyComplete();

    verify(categoryRepo).deleteById(CATEGORY_ID);
    verifyNoMoreInteractions(categoryRepo);
  }

  @Test
  @DisplayName("Should complete when delete all is successful")
  void should_complete_when_deleteAll() {
    // Arrange
    when(categoryRepo.deleteAll()).thenReturn(Mono.empty());

    // Act
    Mono<Void> result = adapter.deleteAll();

    // Assert
    StepVerifier.create(result).verifyComplete();

    verify(categoryRepo).deleteAll();
    verifyNoMoreInteractions(categoryRepo);
  }
}
