package com.cmp_products.dto;

/**
 * Respuesta de base_inventory. Solo se declara el campo que usa el resumen; Jackson ignora los demás.
 */
public record InventoryDto(
        Integer availableUnits) {
}
