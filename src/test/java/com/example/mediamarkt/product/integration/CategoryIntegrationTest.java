package com.example.mediamarkt.product.integration;

import com.example.mediamarkt.product.interfaces.controllers.dto.CategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class CategoryIntegrationTest extends AbstractIntegrationTest {

  @Autowired private WebTestClient webTestClient;

  @Test
  @DisplayName("Integration Test: Should Return All Categories")
  void shouldReturnAllCategories() {
    webTestClient
        .get()
        .uri("/api/v1/category/all")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$")
        .isNotEmpty()
        .jsonPath("$[0].id")
        .exists();
  }

  @Test
  @DisplayName("Integration Test: Should Return a Category By Id")
  void shouldReturnCategoryById() {

    webTestClient
        .get()
        .uri("api/v1/category/all") // get all the categories
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(CategoryDto.class)
        .consumeWith(
            response -> {
              String existingId = response.getResponseBody().get(0).getId(); // searching the id
              webTestClient
                  .get()
                  .uri("api/v1/category/{id}", existingId)
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody()
                  .jsonPath("$.id")
                  .isEqualTo(existingId)
                  .jsonPath("$.name")
                  .exists();
            });
  }

  @Test
  @DisplayName("Integration Test: Should post a new Category")
  void should_Create_New_Category() {
    CategoryDto categoryDto = CategoryDto.builder().id("NEW-CAT-1").name("INT TEST CAT").build();

    webTestClient
        .post()
        .uri("api/v1/category")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(categoryDto)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("NEW-CAT-1")
        .jsonPath("$.name")
        .isEqualTo("INT TEST CAT");
  }

  @Test
  @DisplayName("Integration Test: Should Update an Existing Category")
  void should_Update_New_Category() {
    // Post one category
    CategoryDto categoryDto = CategoryDto.builder().id("UPDATE-CAT-1").name("OLD NAME").build();

    webTestClient
        .post()
        .uri("api/v1/category")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(categoryDto)
        .exchange()
        .expectStatus()
        .isCreated();

    // Update old category
    CategoryDto updateCategoryDto =
        CategoryDto.builder().id("UPDATE-CAT-1").name("NEW NAME").build();

    webTestClient
        .put()
        .uri("api/v1/category/{id}", "UPDATE-CAT-1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updateCategoryDto)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("UPDATE-CAT-1")
        .jsonPath("$.name")
        .isEqualTo("NEW NAME");
  }

  @Test
  @DisplayName("Integration Test: Should Delete an Existing Category")
  void should_Delete_Category() {
    CategoryDto futureDeleteCat =
        CategoryDto.builder().id("DELETE-CAT-1").name("To be Deleted").build();

    // insert
    webTestClient
        .post()
        .uri("api/v1/category")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(futureDeleteCat)
        .exchange()
        .expectStatus()
        .isCreated();

    // delete
    webTestClient
        .delete()
        .uri("api/v1/category/{id}", "DELETE-CAT-1")
        .exchange()
        .expectStatus()
        .isNoContent();

    // verify
    webTestClient
        .get()
        .uri("/api/v1/category/{id}", "DELETE-CAT-1")
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}
