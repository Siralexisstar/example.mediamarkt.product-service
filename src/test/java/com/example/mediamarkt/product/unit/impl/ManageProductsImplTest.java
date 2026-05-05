package com.example.mediamarkt.product.unit.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.application.impl.ManageProductsImpl;
import com.example.mediamarkt.product.application.port.ProductRepositoryPort;
import com.example.mediamarkt.product.domain.exception.ResourceNotFoundException;
import com.example.mediamarkt.product.domain.model.Product;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ManageProductsImplTest {

  @Mock private ProductRepositoryPort productRepositoryPort;

  @InjectMocks private ManageProductsImpl manageProducts;

  private final String PRODUCT_ID = "1";
  private final String PRODUCT_NAME = "Test Product";
  private final String PRODUCT_STATUS = "ACTIVE";
  private final String PRODUCT_LONG_DESCRIPTION = "This is a test product";
  private final String PRODUCT_SHORT_DESCRIPTION = "Short Description";
  private final List<String> PRODUCT_CATEGORY_IDS = List.of("cat1", "cat2");

  @Test
  void should_returnProduct_when_createProduct() {

    // Given
    Product product =
        Product.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .status(PRODUCT_STATUS)
            .longDescription(PRODUCT_LONG_DESCRIPTION)
            .shortDescription(PRODUCT_SHORT_DESCRIPTION)
            .categoryIds(PRODUCT_CATEGORY_IDS)
            .build();

    // When
    when(productRepositoryPort.save(product)).thenReturn(Mono.just(product));

    // Then
    StepVerifier.create(manageProducts.createProduct(product)).expectNext(product).verifyComplete();

    verify(productRepositoryPort).save(any(Product.class));
  }

  @Test
  void should_returnAllProducts() {
    Product product1 =
        Product.builder()
            .id("1")
            .name("Product 1")
            .status("ACTIVE")
            .longDescription("Long 1")
            .shortDescription("Short 1")
            .categoryIds(List.of("cat1"))
            .build();
    Product product2 =
        Product.builder()
            .id("2")
            .name("Product 2")
            .status("INACTIVE")
            .longDescription("Long 2")
            .shortDescription("Short 2")
            .categoryIds(List.of("cat2"))
            .build();
    when(productRepositoryPort.findAll())
        .thenReturn(reactor.core.publisher.Flux.just(product1, product2));
    StepVerifier.create(manageProducts.getAllProducts())
        .expectNext(product1)
        .expectNext(product2)
        .verifyComplete();
    verify(productRepositoryPort).findAll();
  }

  @Test
  void should_returnProductById_whenExists() {
    Product product =
        Product.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .status(PRODUCT_STATUS)
            .longDescription(PRODUCT_LONG_DESCRIPTION)
            .shortDescription(PRODUCT_SHORT_DESCRIPTION)
            .categoryIds(PRODUCT_CATEGORY_IDS)
            .build();
    when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
    StepVerifier.create(manageProducts.getProduct(PRODUCT_ID)).expectNext(product).verifyComplete();
    verify(productRepositoryPort).findById(PRODUCT_ID);
  }

  @Test
  void should_error_whenProductNotFoundById() {
    when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Mono.empty());
    StepVerifier.create(manageProducts.getProduct(PRODUCT_ID))
        .expectError(ResourceNotFoundException.class)
        .verify();
    verify(productRepositoryPort).findById(PRODUCT_ID);
  }

  @Test
  void should_updateProduct_whenExists() {
    Product product =
        Product.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .status(PRODUCT_STATUS)
            .longDescription(PRODUCT_LONG_DESCRIPTION)
            .shortDescription(PRODUCT_SHORT_DESCRIPTION)
            .categoryIds(PRODUCT_CATEGORY_IDS)
            .build();
    Product updatedProduct =
        Product.builder()
            .id(PRODUCT_ID)
            .name("Updated Name")
            .status(PRODUCT_STATUS)
            .longDescription(PRODUCT_LONG_DESCRIPTION)
            .shortDescription(PRODUCT_SHORT_DESCRIPTION)
            .categoryIds(PRODUCT_CATEGORY_IDS)
            .build();
    when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
    when(productRepositoryPort.save(updatedProduct)).thenReturn(Mono.just(updatedProduct));
    StepVerifier.create(manageProducts.updateProduct(PRODUCT_ID, updatedProduct))
        .expectNext(updatedProduct)
        .verifyComplete();
    verify(productRepositoryPort).findById(PRODUCT_ID);
    verify(productRepositoryPort).save(updatedProduct);
  }

  @Test
  void should_error_whenUpdateProductNotFound() {
    Product product =
        Product.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .status(PRODUCT_STATUS)
            .longDescription(PRODUCT_LONG_DESCRIPTION)
            .shortDescription(PRODUCT_SHORT_DESCRIPTION)
            .categoryIds(PRODUCT_CATEGORY_IDS)
            .build();
    when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Mono.empty());
    StepVerifier.create(manageProducts.updateProduct(PRODUCT_ID, product))
        .expectError(ResourceNotFoundException.class)
        .verify();
    verify(productRepositoryPort).findById(PRODUCT_ID);
  }

  @Test
  void should_deleteProduct_whenExists() {
    Product product =
        Product.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .status(PRODUCT_STATUS)
            .longDescription(PRODUCT_LONG_DESCRIPTION)
            .shortDescription(PRODUCT_SHORT_DESCRIPTION)
            .categoryIds(PRODUCT_CATEGORY_IDS)
            .build();
    when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
    when(productRepositoryPort.delete(product)).thenReturn(Mono.empty());
    StepVerifier.create(manageProducts.deleteProduct(PRODUCT_ID)).verifyComplete();
    verify(productRepositoryPort).findById(PRODUCT_ID);
    verify(productRepositoryPort).delete(product);
  }

  @Test
  void should_error_whenDeleteProductNotFound() {
    when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Mono.empty());
    StepVerifier.create(manageProducts.deleteProduct(PRODUCT_ID))
        .expectError(ResourceNotFoundException.class)
        .verify();
    verify(productRepositoryPort).findById(PRODUCT_ID);
  }
}
