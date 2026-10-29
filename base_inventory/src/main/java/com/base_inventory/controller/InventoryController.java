package com.base_inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base_inventory.dto.InventoryDto;
import com.base_inventory.exception.InventoryNotFoundException;
import com.base_inventory.service.InventoryService;

@Controller
@RequestMapping(value = "/microservices/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findByProductId(@PathVariable("productId") String productId)
            throws InventoryNotFoundException {
        InventoryDto inventoryDto = inventoryService.findByProductId(productId);
        return new ResponseEntity<>(inventoryDto, HttpStatus.OK);
    }
}
