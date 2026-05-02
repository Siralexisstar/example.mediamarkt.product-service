package com.example.mediamarkt.product.interfaces.controllers.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductWithCategoryPathDto {
  private ProductDto product;
  private List<List<CategoryDto>> categoryPaths;
}
