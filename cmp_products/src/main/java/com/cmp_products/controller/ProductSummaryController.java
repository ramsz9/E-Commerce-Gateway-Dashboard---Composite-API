package com.cmp_products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmp_products.dto.ProductSummaryDto;
import com.cmp_products.service.ProductSummaryService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/product-summary")
public class ProductSummaryController {

    private final ProductSummaryService productSummaryService;

    public ProductSummaryController(ProductSummaryService productSummaryService) {
        this.productSummaryService = productSummaryService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductSummaryDto>> findSummary(@PathVariable("id") String id) {
        return productSummaryService.findSummary(id)
                .map(summaryDto -> new ResponseEntity<>(summaryDto, HttpStatus.OK));
    }
}
