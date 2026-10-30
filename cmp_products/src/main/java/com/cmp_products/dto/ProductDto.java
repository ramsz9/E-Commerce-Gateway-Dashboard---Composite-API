package com.cmp_products.dto;

/**
 * Respuesta de base_products. Solo se declaran los campos que usa el resumen; Jackson ignora los demás.
 */
public record ProductDto(
        String id,
        String name,
        Double priceUsd) {
}
