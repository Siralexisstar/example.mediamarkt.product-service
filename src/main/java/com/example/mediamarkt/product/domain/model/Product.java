package com.example.mediamarkt.product.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  private String id;

  private String name;

  private String status;

  private String longDescription;

  private String shortDescription;

  private List<String> categoryIds;
}
