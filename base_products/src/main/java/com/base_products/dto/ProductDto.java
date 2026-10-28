package com.base_products.dto;

public record ProductDto(
        String id,
        String name,
        String description,
        String category,
        Double priceUsd) {
}
