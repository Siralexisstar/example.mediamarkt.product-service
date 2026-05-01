package com.example.mediamarkt.product.interfaces.controllers.dto;

import com.example.mediamarkt.product.domain.model.Product;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

  private String id;

  @NotNull(message = "Name is required")
  private String name;

  @NotNull(message = "status is required")
  private String status;

  @NotNull(message = "LongDescription is required")
  private String longDescription;

  @NotNull(message = "ShortDescription is required")
  private String shortDescription;

  @NotNull(message = "Price is required")
  private double price;

  @NotNull(message = "CategoryIds is required")
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
