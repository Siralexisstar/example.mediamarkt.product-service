package com.example.mediamarkt.product.infraestructure.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.mediamarkt.product.infraestructure.persistence.mongo.CategoryDocument;

public interface ReactiveCategoryRepository extends ReactiveMongoRepository<CategoryDocument, String> {

}
