package com.example.mediamarkt.product.interfaces.controllers.dto;

import com.example.mediamarkt.product.domain.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

  private String id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Status is required")
  private String status;

  @NotBlank(message = "Long description is required")
  private String longDescription;

  @NotBlank(message = "Short description is required")
  private String shortDescription;

  @NotEmpty(message = "Category IDs are required")
  private List<String> categoryIds;

  // Mappers for dto's

  public static ProductDto fromDomain(Product product) {
    if (product == null) return null;
    return ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .status(product.getStatus())
        .longDescription(product.getLongDescription())
        .shortDescription(product.getShortDescription())
        .categoryIds(product.getCategoryIds())
        .build();
  }

  public Product toDomain() {
    return Product.builder()
        .id(id)
        .name(name)
        .status(status)
        .longDescription(longDescription)
        .shortDescription(shortDescription)
        .categoryIds(categoryIds)
        .build();
  }
}
