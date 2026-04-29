package com.example.mediamarkt.product.infraestructure.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.mediamarkt.product.infraestructure.persistence.mongo.ProductDocument;

public interface ReactiveProductRepository extends ReactiveMongoRepository<ProductDocument, String> {

}
