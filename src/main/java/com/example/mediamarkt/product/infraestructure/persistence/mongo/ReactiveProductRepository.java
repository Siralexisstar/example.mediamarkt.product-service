package com.example.mediamarkt.product.infraestructure.persistence.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveProductRepository extends ReactiveMongoRepository<ProductDocument, String> {

}
