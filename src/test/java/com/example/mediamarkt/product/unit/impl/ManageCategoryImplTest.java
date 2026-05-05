package com.example.mediamarkt.product.unit.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.application.impl.ManageCategoryImpl;
import com.example.mediamarkt.product.application.port.CategoryRepositoryPort;
import com.example.mediamarkt.product.domain.exception.ResourceNotFoundException;
import com.example.mediamarkt.product.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ManageCategoryImplTest {

  @Mock
  private CategoryRepositoryPort categoryRepositoryPort;

  @InjectMocks
  private ManageCategoryImpl manageCategory;

  private final String CATEGORY_ID = "1";
  private final String CATEGORY_NAME = "Test Category";
  private final String PARENT_CATEGORY_ID = "2";

  @Test
  void should_returnCategory_when_createCategory() {

    // Given
    Category category = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).parentId(PARENT_CATEGORY_ID).build();

    // When
    when(categoryRepositoryPort.save(category)).thenReturn(Mono.just(category));

    // Then
    StepVerifier.create(manageCategory.createCategory(category))
        .expectNext(category)
        .verifyComplete();

    verify(categoryRepositoryPort).save(any(Category.class));
  }

  @Test
  void should_deleteAllCategories() {
    when(categoryRepositoryPort.deleteAll()).thenReturn(Mono.empty());
    StepVerifier.create(manageCategory.deleteCategories()).verifyComplete();
    verify(categoryRepositoryPort).deleteAll();
  }

  @Test
  void should_deleteCategoryById() {
    String id = "1";
    when(categoryRepositoryPort.delete(id)).thenReturn(Mono.empty());
    StepVerifier.create(manageCategory.deleteCategoryById(id)).verifyComplete();
    verify(categoryRepositoryPort).delete(id);
  }

  @Test
  void should_returnAllCategories() {
    Category cat1 = Category.builder().id("1").name("Cat 1").build();
    Category cat2 = Category.builder().id("2").name("Cat 2").build();
    when(categoryRepositoryPort.findAll()).thenReturn(reactor.core.publisher.Flux.just(cat1, cat2));
    StepVerifier.create(manageCategory.getAllCategories())
        .expectNext(cat1)
        .expectNext(cat2)
        .verifyComplete();
    verify(categoryRepositoryPort).findAll();
  }

  @Test
  void should_returnCategoryById_whenExists() {
    Category cat = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
    when(categoryRepositoryPort.findById(CATEGORY_ID)).thenReturn(Mono.just(cat));
    StepVerifier.create(manageCategory.getCategory(CATEGORY_ID)).expectNext(cat).verifyComplete();
    verify(categoryRepositoryPort).findById(CATEGORY_ID);
  }

  @Test
  void should_error_whenCategoryNotFoundById() {
    when(categoryRepositoryPort.findById(CATEGORY_ID)).thenReturn(Mono.empty());
    StepVerifier.create(manageCategory.getCategory(CATEGORY_ID))
        .expectError(ResourceNotFoundException.class)
        .verify();
    verify(categoryRepositoryPort).findById(CATEGORY_ID);
  }

  @Test
  void should_updateCategory_whenExists() {
    Category cat = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
    Category updatedCat = Category.builder().id(CATEGORY_ID).name("Updated").build();
    when(categoryRepositoryPort.findById(CATEGORY_ID)).thenReturn(Mono.just(cat));
    when(categoryRepositoryPort.save(updatedCat)).thenReturn(Mono.just(updatedCat));
    StepVerifier.create(manageCategory.updateCategory(CATEGORY_ID, updatedCat))
        .expectNext(updatedCat)
        .verifyComplete();
    verify(categoryRepositoryPort).findById(CATEGORY_ID);
    verify(categoryRepositoryPort).save(updatedCat);
  }

  @Test
  void should_error_whenUpdateCategoryNotFound() {
    Category cat = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
    when(categoryRepositoryPort.findById(CATEGORY_ID)).thenReturn(Mono.empty());
    StepVerifier.create(manageCategory.updateCategory(CATEGORY_ID, cat))
        .expectError(ResourceNotFoundException.class)
        .verify();
    verify(categoryRepositoryPort).findById(CATEGORY_ID);
  }

  @Test
  void should_returnCategoryPath() {
    Category cat1 = Category.builder().id("1").name("Cat 1").parentId("2").build();
    Category cat2 = Category.builder().id("2").name("Cat 2").build();
    when(categoryRepositoryPort.findById("1")).thenReturn(Mono.just(cat1));
    when(categoryRepositoryPort.findById("2")).thenReturn(Mono.just(cat2));
    // El método getCategoryPath invoca getCategory("1") y luego expande por
    // parentId
    StepVerifier.create(manageCategory.getCategoryPath("1"))
        .expectNextMatches(
            list -> list.size() == 2
                && list.get(0).getId().equals("2")
                && list.get(1).getId().equals("1"))
        .verifyComplete();
    verify(categoryRepositoryPort).findById("1");
    verify(categoryRepositoryPort).findById("2");
  }
}
