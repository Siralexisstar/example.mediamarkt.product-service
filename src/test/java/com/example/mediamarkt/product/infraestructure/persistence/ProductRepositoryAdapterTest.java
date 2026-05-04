package com.example.mediamarkt.product.infraestructure.persistence;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.mediamarkt.product.domain.model.Product;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.ProductDocument;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.ReactiveProductRepository;
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
class ProductRepositoryAdapterTest {

  @Mock private ReactiveProductRepository productRepo;

  @InjectMocks private ProductRepositoryAdapter adapter;

  private final String PRODUCT_ID = "1";
  private final String PRODUCT_NAME = "Laptop";

  @Test
  @DisplayName("Should save product and map back to domain")
  void should_saveProduct_when_save() {
    // Arrange
    Product domainProduct = Product.builder().id(PRODUCT_ID).name(PRODUCT_NAME).build();
    ProductDocument savedDocument =
        ProductDocument.builder().id(PRODUCT_ID).name(PRODUCT_NAME).build();

    when(productRepo.save(any(ProductDocument.class))).thenReturn(Mono.just(savedDocument));

    // Act
    Mono<Product> result = adapter.save(domainProduct);

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(p -> p.getId().equals(PRODUCT_ID) && p.getName().equals(PRODUCT_NAME))
        .verifyComplete();

    verify(productRepo).save(any(ProductDocument.class));
    verifyNoMoreInteractions(productRepo);
  }

  @Test
  @DisplayName("Should return product when found by id")
  void should_returnProduct_when_findById() {
    // Arrange
    ProductDocument document = ProductDocument.builder().id(PRODUCT_ID).name(PRODUCT_NAME).build();

    when(productRepo.findById(PRODUCT_ID)).thenReturn(Mono.just(document));

    // Act
    Mono<Product> result = adapter.findById(PRODUCT_ID);

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(p -> p.getId().equals(PRODUCT_ID) && p.getName().equals(PRODUCT_NAME))
        .verifyComplete();

    verify(productRepo).findById(PRODUCT_ID);
    verifyNoMoreInteractions(productRepo);
  }

  @Test
  @DisplayName("Should return all products")
  void should_returnAllProducts_when_findAll() {
    // Arrange
    ProductDocument doc1 = ProductDocument.builder().id("1").name("Prod 1").build();
    ProductDocument doc2 = ProductDocument.builder().id("2").name("Prod 2").build();

    when(productRepo.findAll()).thenReturn(Flux.just(doc1, doc2));

    // Act
    Flux<Product> result = adapter.findAll();

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(p -> p.getId().equals("1") && p.getName().equals("Prod 1"))
        .expectNextMatches(p -> p.getId().equals("2") && p.getName().equals("Prod 2"))
        .verifyComplete();

    verify(productRepo).findAll();
    verifyNoMoreInteractions(productRepo);
  }

  @Test
  @DisplayName("Should complete when delete is successful")
  void should_complete_when_delete() {
    // Arrange
    Product domainProduct = Product.builder().id(PRODUCT_ID).name(PRODUCT_NAME).build();

    when(productRepo.delete(any(ProductDocument.class))).thenReturn(Mono.empty());

    // Act
    Mono<Void> result = adapter.delete(domainProduct);

    // Assert
    StepVerifier.create(result).verifyComplete();

    verify(productRepo).delete(any(ProductDocument.class));
    verifyNoMoreInteractions(productRepo);
  }
}
