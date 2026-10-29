package com.base_inventory.dto;

public record InventoryDto(
        String productId,
        Integer availableUnits,
        String warehouseLocation,
        Integer deliveryDays) {
}
