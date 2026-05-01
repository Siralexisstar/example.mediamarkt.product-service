package com.example.mediamarkt.product.interfaces.globalException;

import com.example.mediamarkt.product.domain.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
    Map<String, String> response = new HashMap<>();

    response.put("error", "Not found");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<Map<String, String>> handleValidException(WebExchangeBindException ex) {

    Map<String, String> response = new HashMap<>();

    response.put("error", "Validation Error");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintException(
      ConstraintViolationException ex) {

    Map<String, String> response = new HashMap<>();

    response.put("error", "Constraint Violation");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Internal Server Error");
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
