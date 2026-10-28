package com.base_products.service;

import org.springframework.stereotype.Service;

import com.base_products.dto.ProductDto;
import com.base_products.exception.ProductNotFoundException;
import com.base_products.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto findById(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .map(productModel -> productModel.toDto())
                .orElseThrow(() -> new ProductNotFoundException("No existe el producto con id " + id));
    }
}
