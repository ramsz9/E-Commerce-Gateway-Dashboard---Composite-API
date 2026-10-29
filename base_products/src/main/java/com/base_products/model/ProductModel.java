package com.base_products.model;

import com.base_products.dto.ProductDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductModel {

    /**
     * Clave del catálogo (p. ej. "P-101"). No se genera: viene en los datos cargados con data.sql.
     */
    @Id
    @Column(name = "id", columnDefinition = "varchar(20)")
    private String id;
    @Column(name = "name", columnDefinition = "varchar(100)")
    private String name;
    @Column(name = "description", columnDefinition = "varchar(255)")
    private String description;
    @Column(name = "category", columnDefinition = "varchar(50)")
    private String category;
    @Column(name = "price_usd")
    private Double priceUsd;

    public ProductDto toDto() {
        return new ProductDto(
                this.id,
                this.name,
                this.description,
                this.category,
                this.priceUsd);
    }
}
