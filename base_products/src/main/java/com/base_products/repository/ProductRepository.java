package com.base_products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base_products.model.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, String> {
}
