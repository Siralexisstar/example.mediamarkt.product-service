package com.example.mediamarkt.product.domain.model;

import java.util.List;

import org.springframework.data.annotation.Id;

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

    private OnlineStatus onlineStatus;

    private String longDescription;

    private String shortDescription;

    private double price;

    private List<String> categoryIds;

}
