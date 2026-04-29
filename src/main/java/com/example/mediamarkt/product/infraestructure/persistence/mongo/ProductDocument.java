package com.example.mediamarkt.product.infraestructure.persistence.mongo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.mediamarkt.product.domain.model.OnlineStatus;
import com.example.mediamarkt.product.domain.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductDocument {

    @Id
    private String id;

    private String name;

    private OnlineStatus onlineStatus;

    private String longDescription;

    private String shortDescription;

    private double price;

    private List<String> categoryIds;

    // Mappers

    public static ProductDocument fromDomain(Product product) {
        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .onlineStatus(product.getOnlineStatus())
                .longDescription(product.getLongDescription())
                .shortDescription(product.getShortDescription())
                .price(product.getPrice())
                .categoryIds(product.getCategoryIds())
                .build();

    }

    public Product toDomain() {
        return Product.builder()
                .id(id)
                .name(name)
                .onlineStatus(onlineStatus)
                .longDescription(longDescription)
                .shortDescription(shortDescription)
                .categoryIds(categoryIds)
                .build();
    }

}
