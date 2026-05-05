package com.example.mediamarkt.product.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

  @ServiceConnection static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

  static {
    mongoDBContainer.start();
  }
}
