package com.example.mediamarkt.product.unit.interfaces.globalException;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.mediamarkt.product.domain.exception.ResourceNotFoundException;
import com.example.mediamarkt.product.interfaces.globalException.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  @DisplayName("Should return 404 when ResourceNotFoundException is thrown")
  void should_returnNotFound_when_ResourceNotFoundException() {
    // Arrange
    ResourceNotFoundException ex = new ResourceNotFoundException("Product not found");

    // Act
    ResponseEntity<Map<String, String>> response = handler.handleResourceNotFound(ex);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().get("error")).isEqualTo("Not found");
    assertThat(response.getBody().get("message")).isEqualTo("Product not found");
  }

  @Test
  @DisplayName("Should return 400 when ConstraintViolationException is thrown")
  void should_returnBadRequest_when_ConstraintViolationException() {
    // Arrange
    ConstraintViolationException ex = new ConstraintViolationException("Invalid field", null);

    // Act
    ResponseEntity<Map<String, String>> response = handler.handleConstraintException(ex);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().get("error")).isEqualTo("Constraint Violation");
    assertThat(response.getBody().get("message")).isEqualTo("Invalid field");
  }

  @Test
  @DisplayName("Should return 500 when Generic Exception is thrown")
  void should_returnInternalServerError_when_GenericException() {
    // Arrange
    Exception ex = new Exception("Unknown error occurred");

    // Act
    ResponseEntity<Map<String, String>> response = handler.handleGenericException(ex);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().get("error")).isEqualTo("Internal Server Error");
    assertThat(response.getBody().get("message")).isEqualTo("Unknown error occurred");
  }
}
