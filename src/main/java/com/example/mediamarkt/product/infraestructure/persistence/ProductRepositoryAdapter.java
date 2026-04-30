package com.example.mediamarkt.product.infraestructure.persistence;

import com.example.mediamarkt.product.application.port.ProductRepositoryPort;
import com.example.mediamarkt.product.domain.model.Product;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.ProductDocument;
import com.example.mediamarkt.product.infraestructure.persistence.mongo.ReactiveProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

  private final ReactiveProductRepository productRepo;

  @Override
  public Mono<Product> save(Product product) {

    return productRepo.save(ProductDocument.fromDomain(product)).map(ProductDocument::toDomain);
  }

  @Override
  public Mono<Product> findById(String id) {

    return productRepo.findById(id).map(ProductDocument::toDomain);
  }

  @Override
  public Flux<Product> findAll() {

    return productRepo.findAll().map(ProductDocument::toDomain);
  }

  @Override
  public Mono<Void> delete(Product product) {

    return productRepo.delete(ProductDocument.fromDomain(product));
  }
}
