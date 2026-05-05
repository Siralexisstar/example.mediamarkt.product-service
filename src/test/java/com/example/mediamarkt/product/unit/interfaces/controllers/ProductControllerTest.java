package com.example.mediamarkt.product.unit.interfaces.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.application.impl.ManageCategoryImpl;
import com.example.mediamarkt.product.application.impl.ManageProductsImpl;
import com.example.mediamarkt.product.domain.model.Product;
import com.example.mediamarkt.product.interfaces.controllers.ProductController;
import com.example.mediamarkt.product.interfaces.controllers.dto.CategoryDto;
import com.example.mediamarkt.product.interfaces.controllers.dto.ProductDto;
import com.example.mediamarkt.product.interfaces.controllers.dto.ProductWithCategoryPathDto;
import com.example.mediamarkt.product.interfaces.globalException.GlobalExceptionHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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

    ProductDto productDto = buildProductDto();

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

    verify(productsImpl).createProduct(any(Product.class));
  }

  @Test
  void getAllProducts_shouldReturnList() {
    ProductDto productDto = buildProductDto();
    when(productsImpl.getAllProducts())
        .thenReturn(reactor.core.publisher.Flux.just(productDto.toDomain()));
    webTestClient
        .get()
        .uri("/api/v1/products/all")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ProductDto.class)
        .contains(productDto);

    verify(productsImpl).getAllProducts();
  }

  @Test
  void getProductById_shouldReturnProduct() {
    ProductDto productDto = buildProductDto();
    when(productsImpl.getProduct("1")).thenReturn(Mono.just(productDto.toDomain()));
    webTestClient
        .get()
        .uri("/api/v1/products/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ProductDto.class)
        .isEqualTo(productDto);

    verify(productsImpl).getProduct("1");
  }

  @Test
  void deleteProduct_shouldReturnNoContent() {
    when(productsImpl.deleteProduct("1")).thenReturn(Mono.empty());
    webTestClient.delete().uri("/api/v1/products/1").exchange().expectStatus().isNoContent();
    verify(productsImpl).deleteProduct("1");
  }

  @Test
  void updateProduct_shouldReturnUpdatedProduct() {
    ProductDto productDto = buildProductDto();

    when(productsImpl.updateProduct(eq("1"), any(Product.class)))
        .thenReturn(Mono.just(productDto.toDomain()));

    webTestClient
        .put()
        .uri("/api/v1/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(productDto)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("Test Product")
        .jsonPath("$.status")
        .isEqualTo("ACTIVE");

    verify(productsImpl).updateProduct(eq("1"), any(Product.class));
  }

  @Test
  void getProductsWithCategoryPath_shouldReturnProductWithCategoryPaths() {
    ProductDto productDto = buildProductDto();
    // Simula que el producto tiene dos categorías y cada una tiene un path de una
    // sola categoría
    List<CategoryDto> catPath1 = List.of(CategoryDto.builder().id("cat1").name("Cat 1").build());
    List<CategoryDto> catPath2 = List.of(CategoryDto.builder().id("cat2").name("Cat 2").build());
    ProductWithCategoryPathDto response =
        ProductWithCategoryPathDto.builder()
            .product(productDto)
            .categoryPaths(List.of(catPath1, catPath2))
            .build();
    when(productsImpl.getProduct("1")).thenReturn(Mono.just(productDto.toDomain()));
    when(categoryImpl.getCategoryPath("cat1"))
        .thenReturn(Mono.just(List.of(catPath1.get(0).toDomain())));
    when(categoryImpl.getCategoryPath("cat2"))
        .thenReturn(Mono.just(List.of(catPath2.get(0).toDomain())));

    webTestClient
        .get()
        .uri("/api/v1/products/1/with-category-path")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ProductWithCategoryPathDto.class)
        .isEqualTo(response);

    verify(productsImpl).getProduct("1");
    verify(categoryImpl).getCategoryPath("cat1");
  }

  public static final ProductDto buildProductDto() {
    return ProductDto.builder()
        .id("1")
        .name("Test Product")
        .status("ACTIVE")
        .longDescription("This is a test product")
        .shortDescription("Short Description")
        .categoryIds(List.of("cat1", "cat2"))
        .build();
  }
}
