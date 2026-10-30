package com.cmp_products.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cmp_products.dto.ExchangeRateDto;
import com.cmp_products.dto.InventoryDto;
import com.cmp_products.dto.ProductDto;
import com.cmp_products.dto.ProductSummaryDto;
import com.cmp_products.exception.ProductNotFoundException;

import reactor.core.publisher.Mono;

@Service
public class ProductSummaryService {

    @Value("${base.url.products}")
    private String baseUrlProducts;

    @Value("${base.url.inventory}")
    private String baseUrlInventory;

    @Value("${external.url.exchange-rate}")
    private String externalUrlExchangeRate;

    private final WebClient webClient;

    public ProductSummaryService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Mono.zip lanza las tres peticiones al mismo tiempo y espera a que las tres respondan.
     * Si una falla, el resumen completo falla y el error llega al ExceptionHandler.
     */
    public Mono<ProductSummaryDto> findSummary(String id) {
        return Mono.zip(findProduct(id), findInventory(id), findExchangeRate())
                .map(results -> toSummary(results.getT1(), results.getT2(), results.getT3()));
    }

    private Mono<ProductDto> findProduct(String id) {
        return webClient.get()
                .uri(baseUrlProducts + "/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(),
                        response -> Mono.error(new ProductNotFoundException("No existe el producto con id " + id)))
                .bodyToMono(ProductDto.class);
    }

    private Mono<InventoryDto> findInventory(String id) {
        return webClient.get()
                .uri(baseUrlInventory + "/{productId}", id)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(),
                        response -> Mono.error(new ProductNotFoundException("No hay inventario del producto con id " + id)))
                .bodyToMono(InventoryDto.class);
    }

    private Mono<ExchangeRateDto> findExchangeRate() {
        return webClient.get()
                .uri(externalUrlExchangeRate)
                .retrieve()
                .bodyToMono(ExchangeRateDto.class);
    }

    private ProductSummaryDto toSummary(ProductDto product, InventoryDto inventory, ExchangeRateDto exchangeRate) {
        Double usdToMxn = exchangeRate.rates().get("MXN");
        // Precio en pesos redondeado a 2 decimales
        Double priceMxn = Math.round(product.priceUsd() * usdToMxn * 100) / 100.0;
        String stockStatus = inventory.availableUnits() > 0 ? "IN_STOCK" : "OUT_OF_STOCK";

        return new ProductSummaryDto(
                product.id(),
                product.name(),
                product.priceUsd(),
                priceMxn,
                stockStatus,
                inventory.availableUnits(),
                "Frankfurter External API",
                "SUCCESS");
    }
}
