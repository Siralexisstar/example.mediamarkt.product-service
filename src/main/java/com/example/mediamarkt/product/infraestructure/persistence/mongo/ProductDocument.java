package com.example.mediamarkt.product.infraestructure.persistence.mongo;

import com.example.mediamarkt.product.domain.model.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductDocument {

  @Id private String id;

  private String name;

  private String status;

  private String longDescription;

  private String shortDescription;

  private List<String> categoryIds;

  // Mappers

  public static ProductDocument fromDomain(Product product) {
    if (product == null) return null;
    return ProductDocument.builder()
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
