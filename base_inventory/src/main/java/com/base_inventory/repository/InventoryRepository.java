package com.base_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base_inventory.model.InventoryModel;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, String> {
}
