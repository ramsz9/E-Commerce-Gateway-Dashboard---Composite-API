package com.base_inventory.model;

import com.base_inventory.dto.InventoryDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class InventoryModel {

    /**
     * Clave del producto en el catálogo (p. ej. "P-101"). No se genera: viene en los datos cargados con data.sql.
     */
    @Id
    @Column(name = "product_id", columnDefinition = "varchar(20)")
    private String productId;
    @Column(name = "available_units")
    private Integer availableUnits;
    @Column(name = "warehouse_location", columnDefinition = "varchar(100)")
    private String warehouseLocation;
    @Column(name = "delivery_days")
    private Integer deliveryDays;

    public InventoryDto toDto() {
        return new InventoryDto(
                this.productId,
                this.availableUnits,
                this.warehouseLocation,
                this.deliveryDays);
    }
}
