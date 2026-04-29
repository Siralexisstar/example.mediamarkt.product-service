package com.example.mediamarkt.product.infraestructure.persistence.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.mediamarkt.product.domain.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categories")
public class CategoryDocument {

    @Id
    private String id;

    private String name;

    private String parentId;

    // Mappers.

    public static CategoryDocument fromDomain(Category category) {
        return CategoryDocument.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParentId())
                .build();
    }

    public Category toDomain() {
        return Category.builder()
                .id(id)
                .name(name)
                .parentId(parentId)
                .build();
    }

}
