package com.base_inventory.service;

import org.springframework.stereotype.Service;

import com.base_inventory.dto.InventoryDto;
import com.base_inventory.exception.InventoryNotFoundException;
import com.base_inventory.repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryDto findByProductId(String productId) throws InventoryNotFoundException {
        return inventoryRepository.findById(productId)
                .map(inventoryModel -> inventoryModel.toDto())
                .orElseThrow(() -> new InventoryNotFoundException("No hay inventario del producto con id " + productId));
    }
}
