package com.cmp_products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contrato consolidado que recibe el cliente. @JsonProperty da a cada campo el nombre snake_case del enunciado.
 */
public record ProductSummaryDto(
        @JsonProperty("product_id") String productId,
        @JsonProperty("title") String title,
        @JsonProperty("price_usd") Double priceUsd,
        @JsonProperty("price_mxn") Double priceMxn,
        @JsonProperty("stock_status") String stockStatus,
        @JsonProperty("available_units") Integer availableUnits,
        @JsonProperty("currency_conversion_source") String currencyConversionSource,
        @JsonProperty("status") String status) {
}
