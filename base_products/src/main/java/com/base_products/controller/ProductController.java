package com.base_products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base_products.dto.ProductDto;
import com.base_products.exception.ProductNotFoundException;
import com.base_products.service.ProductService;

@Controller
@RequestMapping(value = "/microservices/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) throws ProductNotFoundException {
        ProductDto productDto = productService.findById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}
