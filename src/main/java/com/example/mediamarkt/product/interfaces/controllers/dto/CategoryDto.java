package com.example.mediamarkt.product.interfaces.controllers.dto;

import com.example.mediamarkt.product.domain.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

  private String id;

  @NotNull(message = "Name is required")
  private String name;

  private String parentId;

  // Mappers for dto's

  public static CategoryDto fromDomain(Category category) {
    if (category == null) return null;
    return CategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .parentId(category.getParentId())
        .build();
  }

  public Category toDomain() {
    return Category.builder().id(id).name(name).parentId(parentId).build();
  }
}
