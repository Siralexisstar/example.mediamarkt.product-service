package com.example.mediamarkt.product.unit.interfaces.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.application.impl.ManageCategoryImpl;
import com.example.mediamarkt.product.domain.model.Category;
import com.example.mediamarkt.product.interfaces.controllers.CategoryController;
import com.example.mediamarkt.product.interfaces.controllers.dto.CategoryDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(CategoryController.class)
class CategoryControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockitoBean
  private ManageCategoryImpl categoryImpl;

  private final String CATEGORY_ID = "1";
  private final String CATEGORY_NAME = "Electronics";
  private final String PARENT_ID = "0";

  @Test
  @DisplayName("Should return created category when create is called")
  void should_returnCreatedCategory_when_createCategory() {
    // Arrange
    CategoryDto inputDto = CategoryDto.builder().id(CATEGORY_ID).name(CATEGORY_NAME).parentId(PARENT_ID).build();

    Category domainCategory = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).parentId(PARENT_ID).build();

    when(categoryImpl.createCategory(any(Category.class))).thenReturn(Mono.just(domainCategory));

    // Act & Assert
    webTestClient
        .post()
        .uri("/api/v1/category")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(inputDto)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(CATEGORY_ID)
        .jsonPath("$.name")
        .isEqualTo(CATEGORY_NAME)
        .jsonPath("$.parentId")
        .isEqualTo(PARENT_ID);

    verify(categoryImpl).createCategory(any(Category.class));
    verifyNoMoreInteractions(categoryImpl);
  }

  @Test
  @DisplayName("Should return category when found by id")
  void should_returnCategory_when_getById() {
    // Arrange
    Category domainCategory = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).parentId(PARENT_ID).build();

    when(categoryImpl.getCategory(CATEGORY_ID)).thenReturn(Mono.just(domainCategory));

    // Act & Assert
    webTestClient
        .get()
        .uri("/api/v1/category/{id}", CATEGORY_ID)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(CATEGORY_ID)
        .jsonPath("$.name")
        .isEqualTo(CATEGORY_NAME);

    verify(categoryImpl).getCategory(CATEGORY_ID);
    verifyNoMoreInteractions(categoryImpl);
  }

  @Test
  @DisplayName("Should return all categories")
  void should_returnAllCategories_when_getAll() {
    // Arrange
    Category cat1 = Category.builder().id("1").name("Cat 1").build();
    Category cat2 = Category.builder().id("2").name("Cat 2").build();

    when(categoryImpl.getAllCategories()).thenReturn(Flux.just(cat1, cat2));

    // Act & Assert
    webTestClient
        .get()
        .uri("/api/v1/category/all")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo("1")
        .jsonPath("$[1].id")
        .isEqualTo("2");

    verify(categoryImpl).getAllCategories();
    verifyNoMoreInteractions(categoryImpl);
  }

  @Test
  @DisplayName("Should return the entire category path")
  void should_returnCategoryPath_when_getEntirePathCategory() {
    // Arrange
    Category cat1 = Category.builder().id("1").name("Parent").build();
    Category cat2 = Category.builder().id("2").name("Child").parentId("1").build();

    when(categoryImpl.getCategoryPath("2")).thenReturn(Mono.just(List.of(cat1, cat2)));

    // Act & Assert
    webTestClient
        .get()
        .uri("/api/v1/category/{id}/path", "2")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo("1")
        .jsonPath("$[1].id")
        .isEqualTo("2");

    verify(categoryImpl).getCategoryPath("2");
    verifyNoMoreInteractions(categoryImpl);
  }

  @Test
  @DisplayName("Should return updated category when update is called")
  void should_returnUpdatedCategory_when_update() {
    // Arrange
    CategoryDto inputDto = CategoryDto.builder().id(CATEGORY_ID).name("Updated Name").build();

    Category updatedDomain = Category.builder().id(CATEGORY_ID).name("Updated Name").build();

    when(categoryImpl.updateCategory(eq(CATEGORY_ID), any(Category.class)))
        .thenReturn(Mono.just(updatedDomain));

    // Act & Assert
    webTestClient
        .put()
        .uri("/api/v1/category/{id}", CATEGORY_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(inputDto)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("Updated Name");

    verify(categoryImpl).updateCategory(eq(CATEGORY_ID), any(Category.class));
    verifyNoMoreInteractions(categoryImpl);
  }

  @Test
  @DisplayName("Should return no content when delete by id")
  void should_returnNoContent_when_delete() {
    // Arrange
    when(categoryImpl.deleteCategoryById(CATEGORY_ID)).thenReturn(Mono.empty());

    // Act & Assert
    webTestClient
        .delete()
        .uri("/api/v1/category/{id}", CATEGORY_ID)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(categoryImpl).deleteCategoryById(CATEGORY_ID);
    verifyNoMoreInteractions(categoryImpl);
  }

  @Test
  @DisplayName("Should return no content when delete all")
  void should_returnNoContent_when_deleteAll() {
    // Arrange
    when(categoryImpl.deleteCategories()).thenReturn(Mono.empty());

    // Act & Assert
    webTestClient.delete().uri("/api/v1/category").exchange().expectStatus().isNoContent();

    verify(categoryImpl).deleteCategories();
    verifyNoMoreInteractions(categoryImpl);
  }
}
