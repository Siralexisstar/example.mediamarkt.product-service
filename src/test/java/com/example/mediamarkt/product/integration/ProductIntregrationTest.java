package com.example.mediamarkt.product.integration;

import com.example.mediamarkt.product.interfaces.controllers.dto.ProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ProductIntregrationTest extends AbstractIntegrationTest {

  @Test
  @DisplayName("Integration Test: Should Return All Products")
  void shouldReturnAllProducts() {
    webTestClient
        .get()
        .uri("/api/v1/products/all")
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
  @DisplayName("Integration Test: Should Return a Product By Id")
  void shouldReturnProductById() {
    // 1. Get all to find an existing ID from DataLoader
    webTestClient
        .get()
        .uri("/api/v1/products/all")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ProductDto.class)
        .consumeWith(
            response -> {
              String existingId = response.getResponseBody().get(0).getId();

              // 2. Fetch by that ID
              webTestClient
                  .get()
                  .uri("/api/v1/products/{id}", existingId)
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
  @DisplayName("Integration Test: Should Return a Product with its Category Path")
  void shouldReturnProductWithCategoryPath() {
    webTestClient
        .get()
        .uri("/api/v1/products/all")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ProductDto.class)
        .consumeWith(
            response -> {
              String existingId = response.getResponseBody().get(0).getId();

              webTestClient
                  .get()
                  .uri("/api/v1/products/{id}/with-category-path", existingId)
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody()
                  .jsonPath("$.product.id")
                  .isEqualTo(existingId)
                  .jsonPath("$.categoryPaths")
                  .exists();
            });
  }

  @Test
  @DisplayName("Integration Test: Should Create a New Product")
  void should_Create_New_Product() {
    ProductDto newProduct =
        ProductDto.builder()
            .id("NEW-PROD-1")
            .name("Test Product")
            .shortDescription("Short")
            .longDescription("Long")
            .status("ACTIVE")
            .categoryIds(List.of())
            .build();

    webTestClient
        .post()
        .uri("/api/v1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(newProduct)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("NEW-PROD-1")
        .jsonPath("$.name")
        .isEqualTo("Test Product");
  }

  @Test
  @DisplayName("Integration Test: Should Update an Existing Product")
  void should_Update_Product() {
    // Post one product
    ProductDto productDto =
        ProductDto.builder().id("UPDATE-PROD-1").name("OLD PRODUCT").status("DRAFT").build();

    webTestClient
        .post()
        .uri("/api/v1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(productDto)
        .exchange()
        .expectStatus()
        .isCreated();

    // Update old product
    ProductDto updateProductDto =
        ProductDto.builder().id("UPDATE-PROD-1").name("NEW PRODUCT").status("ACTIVE").build();

    webTestClient
        .put()
        .uri("/api/v1/products/{id}", "UPDATE-PROD-1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updateProductDto)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo("UPDATE-PROD-1")
        .jsonPath("$.name")
        .isEqualTo("NEW PRODUCT")
        .jsonPath("$.status")
        .isEqualTo("ACTIVE");
  }

  @Test
  @DisplayName("Integration Test: Should Delete an Existing Product")
  void should_Delete_Product() {
    ProductDto futureDeleteProd =
        ProductDto.builder().id("DELETE-PROD-1").name("To be Deleted").build();

    // insert
    webTestClient
        .post()
        .uri("/api/v1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(futureDeleteProd)
        .exchange()
        .expectStatus()
        .isCreated();

    // delete
    webTestClient
        .delete()
        .uri("/api/v1/products/{id}", "DELETE-PROD-1")
        .exchange()
        .expectStatus()
        .isNoContent();

    // verify
    webTestClient
        .get()
        .uri("/api/v1/products/{id}", "DELETE-PROD-1")
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}
