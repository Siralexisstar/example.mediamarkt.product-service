package com.example.mediamarkt.product.interfaces.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.application.impl.ManageCategoryImpl;
import com.example.mediamarkt.product.application.impl.ManageProductsImpl;
import com.example.mediamarkt.product.domain.model.Product;
import com.example.mediamarkt.product.interfaces.controllers.dto.ProductDto;
import com.example.mediamarkt.product.interfaces.globalException.GlobalExceptionHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
@RequiredArgsConstructor
public class ProductControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockitoBean private ManageProductsImpl productsImpl;

  @MockitoBean private ManageCategoryImpl categoryImpl;

  @Test
  void createProduct_shouldReturnCreatedProduct() {

    // we have to mock the final product created
    ProductDto productDto =
        ProductDto.builder()
            .id("1")
            .name("Test Product")
            .status("ACTIVE")
            .longDescription("This is a test product")
            .shortDescription("Short Description")
            .categoryIds(List.of("cat1", "cat2"))
            .build();

    // Product product = productDto.toDomain();

    // when
    when(productsImpl.createProduct(any(Product.class)))
        .thenReturn(Mono.just(productDto.toDomain()));

    // then
    webTestClient
        .post()
        .uri("/api/v1/products")
        .bodyValue(productDto)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ProductDto.class)
        .isEqualTo(productDto);
  }

  @Test
  void testDelete() {}

  @Test
  void testGetAllProducts() {}

  @Test
  void testGetById() {}

  @Test
  void testGetProductsWithCategoryPath() {}

  @Test
  void testUpdate() {}
}
