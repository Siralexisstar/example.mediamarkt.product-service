package com.example.mediamarkt.product.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

  @ServiceConnection static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

  static {
    mongoDBContainer.start();
  }

  @Autowired protected WebTestClient webTestClient;

  @BeforeEach
  void setUpWebTestClient() {
    // Increase the default 256KB buffer limit to 5MB to avoid DataBufferLimitException
    this.webTestClient =
        this.webTestClient
            .mutate()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
            .build();
  }
}
